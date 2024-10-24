package com.scheduler.CustomApiSchedulerProject.controllers;

import com.scheduler.CustomApiSchedulerProject.entites.ApiConfigurationDetail;
import com.scheduler.CustomApiSchedulerProject.services.ApiConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiconfig")
public class ApiConfigurationController {
    private ApiConfigurationService apiConfigService;

    @Autowired
    public ApiConfigurationController(ApiConfigurationService apiConfigService){
        this.apiConfigService = apiConfigService;
    }

    //create API
    @PostMapping("/create")
    public ResponseEntity<ApiConfigurationDetail> createApi(@RequestBody ApiConfigurationDetail apiConfigDetail){
        System.out.println("this is create api");
        String methodname = apiConfigDetail.getHttpMethod();
        System.out.println("method name is: "+ methodname);
        ApiConfigurationDetail result  = apiConfigService.addApi(apiConfigDetail);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getAllApi")
    public ResponseEntity<List<ApiConfigurationDetail>> fetchAllApi(){
        List<ApiConfigurationDetail> apiList = apiConfigService.getAllApi();
        return ResponseEntity.ok().body(apiList);

    }

    @PutMapping("/updateApi/{apiId}")
    public ResponseEntity<ApiConfigurationDetail> updateApi(@PathVariable int apiId, @RequestBody ApiConfigurationDetail apiConfigDetail){
        ApiConfigurationDetail updatedApiDtl = apiConfigService.updateApi(apiId,apiConfigDetail);
        return ResponseEntity.ok().body(updatedApiDtl);
    }
}