package com.scheduler.CustomApiSchedulerProject.repositories;

import com.scheduler.CustomApiSchedulerProject.entites.ApiConfigurationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiConfigRepository extends JpaRepository<ApiConfigurationDetail,Integer> {
}
