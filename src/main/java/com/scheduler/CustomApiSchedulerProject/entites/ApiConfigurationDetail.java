package com.scheduler.CustomApiSchedulerProject.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.id.factory.spi.GenerationTypeStrategy;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApiConfigurationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int apiId;
    @Column(nullable = false)

    private String url;
    @Column(nullable = false)
    private String httpMethod;
    private LocalTime nextExecutionTime;
    @Column(nullable = false)
    private LocalTime lastExecutionTime;
    @Column(nullable = false)
    private Duration timeInterval;
    private int maxNoOfTimeApiScheduled = 10;
    private String requestBody;

}
