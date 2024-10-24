package com.scheduler.CustomApiSchedulerProject.repositories;

import com.scheduler.CustomApiSchedulerProject.entites.ApiExecutionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiExecutionRepository extends JpaRepository<ApiExecutionDetail,Integer> {
}
