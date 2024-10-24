package com.scheduler.CustomApiSchedulerProject.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiExecutionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int executionId;
    @ManyToOne
    @JoinColumn(name = "apiId",nullable = false)
    private ApiConfigurationDetail apiConfigDetail;
    private LocalTime executionTime;
    private boolean executionStatus;
    private Duration responseTime;
    private int responseCode;
    private String errorMessage;
}
