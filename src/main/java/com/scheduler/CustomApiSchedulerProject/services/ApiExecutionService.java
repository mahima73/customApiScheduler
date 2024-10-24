package com.scheduler.CustomApiSchedulerProject.services;

import com.scheduler.CustomApiSchedulerProject.entites.ApiExecutionDetail;

import java.util.List;

public interface ApiExecutionService {
    //writes execution detail when api was executed.
    void logExecution(ApiExecutionDetail apiExecutionDtl);
    //this will re-execute if status is failed previously
    void retryExecution(int executionId);

    //this will have all execution details with respect to API ID
    List<ApiExecutionDetail> getApiExecutionHistory(int apiId);
    ApiExecutionDetail getLastApiExecution(int apiId);

}
