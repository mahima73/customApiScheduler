package com.scheduler.CustomApiSchedulerProject.services.impl;

import com.scheduler.CustomApiSchedulerProject.entites.ApiExecutionDetail;
import com.scheduler.CustomApiSchedulerProject.repositories.ApiExecutionRepository;
import com.scheduler.CustomApiSchedulerProject.services.ApiExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiExecutionServiceImpl implements ApiExecutionService {
    private ApiExecutionRepository apiExecutionRepo;

    @Autowired
    public ApiExecutionServiceImpl(ApiExecutionRepository apiExecutionRepo){
        this.apiExecutionRepo = apiExecutionRepo;
    }
    @Override
    public void logExecution(ApiExecutionDetail apiExecutionDtl) {
        apiExecutionRepo.save(apiExecutionDtl);
    }

    @Override
    public void retryExecution(int executionId) {

    }

    @Override
    public List<ApiExecutionDetail> getApiExecutionHistory(int apiId) {
        return null;
    }

    @Override
    public ApiExecutionDetail getLastApiExecution(int apiId) {
        return null;
    }
}
