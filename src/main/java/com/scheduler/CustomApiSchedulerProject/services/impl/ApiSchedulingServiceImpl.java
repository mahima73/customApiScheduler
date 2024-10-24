package com.scheduler.CustomApiSchedulerProject.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scheduler.CustomApiSchedulerProject.entites.ApiConfigurationDetail;
import com.scheduler.CustomApiSchedulerProject.entites.ApiExecutionDetail;
import com.scheduler.CustomApiSchedulerProject.enums.HttpMethods;
import com.scheduler.CustomApiSchedulerProject.services.ApiConfigurationService;
import com.scheduler.CustomApiSchedulerProject.services.ApiExecutionService;
import com.scheduler.CustomApiSchedulerProject.services.ApiSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
//import java.net.http.HttpHeaders;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiSchedulingServiceImpl implements ApiSchedulingService {
    private ApiConfigurationService apiConfigService;
    private static final Duration TIME_WINDOW = Duration.ofMinutes(1);
    private ApiExecutionService apiExecutionService;
    private RestTemplate restTemplate;

    @Autowired
    public ApiSchedulingServiceImpl(ApiConfigurationService apiConfigService,ApiExecutionService apiExecutionService,RestTemplate restTemplate){
        this.apiConfigService = apiConfigService;
        this.apiExecutionService = apiExecutionService;
        this.restTemplate = restTemplate;
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void scheduleApiExecutions() {
        System.out.println("inside scheduled");
        List<ApiConfigurationDetail> apiList = apiConfigService.getAllApi();
        List<ApiConfigurationDetail> filteredList = apiList.stream().
                filter(e -> isExecutionTimeValid(e.getNextExecutionTime(),LocalTime.now())).
                collect(Collectors.toList());
        System.out.println(filteredList);
        filteredList.stream().forEach(e -> executeApi(e));
    }
    private boolean isExecutionTimeValid(LocalTime nextExecutionTime,LocalTime currentTime){
        LocalTime endTime = nextExecutionTime.plus(TIME_WINDOW);
        return (currentTime.equals(nextExecutionTime) || currentTime.isAfter(nextExecutionTime)) && (currentTime.isBefore(endTime));
    }
    private void executeApi(ApiConfigurationDetail configDetail){
        System.out.println("executing this aPI");
        long startTime = System.currentTimeMillis();

        //process starts
        boolean executionStatus = false;
        int responseCode = 0;
        String errorMessage = null;
        String requestBody = configDetail.getRequestBody();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ObjectMapper objMapper = new ObjectMapper();
            Map<String, Object> parsedBody = objMapper.readValue(requestBody, Map.class);
            requestBody = objMapper.writeValueAsString(parsedBody);
            HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    configDetail.getUrl(),
                    HttpMethod.valueOf(configDetail.getHttpMethod()),
                    entity,String.class);
            responseCode = response.getStatusCode().value();
            System.out.println("Execution status: "+ executionStatus);
            executionStatus = true;
        }catch (Exception e) {
            errorMessage = e.getMessage();
            executionStatus = false;
        }finally {
            long endTime = System.currentTimeMillis();
            Duration responseTime = Duration.ofMillis(endTime - startTime);

            //Save API Execution details
            ApiExecutionDetail executionDetail = new ApiExecutionDetail();
            executionDetail.setApiConfigDetail(configDetail);
            executionDetail.setExecutionStatus(executionStatus);
            executionDetail.setExecutionTime(LocalTime.now());
            executionDetail.setErrorMessage(errorMessage);
            executionDetail.setResponseCode(responseCode);
            executionDetail.setResponseTime(responseTime);
            apiExecutionService.logExecution(executionDetail);
            //Update next and last execution time in API config table
            if(executionStatus) {
                configDetail.setNextExecutionTime(configDetail.getNextExecutionTime().plus(configDetail.getTimeInterval()));
                configDetail.setLastExecutionTime(LocalTime.now());
                apiConfigService.updateApi(configDetail.getApiId(),configDetail);
            }
        }
    }

}
