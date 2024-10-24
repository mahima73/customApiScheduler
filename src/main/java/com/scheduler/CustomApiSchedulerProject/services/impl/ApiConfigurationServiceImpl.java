package com.scheduler.CustomApiSchedulerProject.services.impl;

import com.scheduler.CustomApiSchedulerProject.entites.ApiConfigurationDetail;
import com.scheduler.CustomApiSchedulerProject.enums.HttpMethods;
import com.scheduler.CustomApiSchedulerProject.exceptions.ApiNotFoundException;
import com.scheduler.CustomApiSchedulerProject.repositories.ApiConfigRepository;
import com.scheduler.CustomApiSchedulerProject.services.ApiConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApiConfigurationServiceImpl implements ApiConfigurationService {
    private final ApiConfigRepository apiConfigRepo;
    @Autowired
    public ApiConfigurationServiceImpl(ApiConfigRepository apiConfigRepo){
        this.apiConfigRepo = apiConfigRepo;
    }

    private boolean validateHttpMethod(String inputMethod){
        boolean isValidHttpMethod = false;
        for(HttpMethods httpMethod: HttpMethods.values()){
            if(inputMethod.equals(httpMethod.toString())){
                isValidHttpMethod = true;
                break;
            }
        }
        return isValidHttpMethod;
    }
    @Override
    @CacheEvict(value = "apiConfigurations",allEntries = true)
    public ApiConfigurationDetail addApi(ApiConfigurationDetail apiConfigDetail) {
        //Validation logic
        String inputMethod = apiConfigDetail.getHttpMethod().toUpperCase();
        boolean isValidHttpMethod = validateHttpMethod(inputMethod);
        String requestBody = apiConfigDetail.getRequestBody();
        apiConfigDetail.setNextExecutionTime(apiConfigDetail.getLastExecutionTime().plus(apiConfigDetail.getTimeInterval()));

        if(!isValidHttpMethod){
            throw new IllegalArgumentException("Invalid HTTP Method passed!");
        }
        if(requestBody == null && !(inputMethod.equals("GET") || inputMethod.equals("DELETE"))) {
            throw new IllegalArgumentException("Invalid request body. Apart from GET/DELETE body should be passed");
        }

        //If all goes fine then just save the body to the Repository
        return apiConfigRepo.save(apiConfigDetail);
    }

    @Override
    @CacheEvict(value = "apiConfigurations", allEntries = true)
    public ApiConfigurationDetail updateApi(int apiId,ApiConfigurationDetail apiConfigDetail) {

        Optional<ApiConfigurationDetail> existingApiConfigDtl = apiConfigRepo.findById(apiId);
        if(existingApiConfigDtl.isEmpty()){
            throw new ApiNotFoundException("Api with given Id: "+ apiConfigDetail.getApiId() + " is not found!");
        }
        //Validation
        String inputMethod = apiConfigDetail.getHttpMethod().toUpperCase();
        boolean isValidHttpMethod = validateHttpMethod(inputMethod);
        if(!isValidHttpMethod){
            throw new IllegalArgumentException("Invalid HTTP Method passed!");
        }

        //update details
        ApiConfigurationDetail resultApiConfigDtl = existingApiConfigDtl.get();
        resultApiConfigDtl.setUrl(apiConfigDetail.getUrl());
        resultApiConfigDtl.setMaxNoOfTimeApiScheduled(apiConfigDetail.getMaxNoOfTimeApiScheduled());
        resultApiConfigDtl.setHttpMethod(apiConfigDetail.getHttpMethod());
        resultApiConfigDtl.setTimeInterval(apiConfigDetail.getTimeInterval());
        resultApiConfigDtl.setNextExecutionTime(apiConfigDetail.getLastExecutionTime().plus(apiConfigDetail.getTimeInterval()));
        resultApiConfigDtl.setLastExecutionTime(apiConfigDetail.getLastExecutionTime());
        resultApiConfigDtl.setRequestBody(apiConfigDetail.getRequestBody());
        return apiConfigRepo.save(resultApiConfigDtl);
    }

    @Override
    @CacheEvict(value = "apiConfigurations", allEntries = true)
    public void deleteApi(int apiId) {
        Optional<ApiConfigurationDetail> apiConfigDtl = apiConfigRepo.findById(apiId);
        if(apiConfigDtl.isEmpty()){
            throw new ApiNotFoundException("Api with Id: "+ apiId +" not found");
        }
        apiConfigRepo.deleteById(apiId);
    }

    @Override
    @Cacheable("apiConfigurations")
    public List<ApiConfigurationDetail> getAllApi() {
        List<ApiConfigurationDetail> apiConfigRepositories = apiConfigRepo.findAll();
        System.out.println("inside get all api");
        return apiConfigRepositories;
    }
}
