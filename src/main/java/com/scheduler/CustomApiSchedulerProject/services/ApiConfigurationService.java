package com.scheduler.CustomApiSchedulerProject.services;

import com.scheduler.CustomApiSchedulerProject.entites.ApiConfigurationDetail;

import java.util.List;

public interface ApiConfigurationService {
    ApiConfigurationDetail addApi(ApiConfigurationDetail apiConfigDetail);
    ApiConfigurationDetail updateApi(int apiId, ApiConfigurationDetail apiConfigDetail);
    void deleteApi(int apiId);
    List<ApiConfigurationDetail> getAllApi();
}
