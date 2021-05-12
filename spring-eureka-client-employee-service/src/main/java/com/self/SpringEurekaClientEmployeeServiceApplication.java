package com.self;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringEurekaClientEmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEurekaClientEmployeeServiceApplication.class, args);
	}

}
