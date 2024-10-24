package com.scheduler.CustomApiSchedulerProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class CustomApiSchedulerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomApiSchedulerProjectApplication.class, args);
	}

	@Bean
	public RestTemplate createBean(){
		return new RestTemplate();
	}


}
