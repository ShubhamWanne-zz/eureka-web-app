package com.self.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.self.loader.EmployeeLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.self")
public class EmployeeConfiguration {
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private int mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDB;

    @Bean
    public EmployeeLoader employeeLoader(){
        return new EmployeeLoader();
    }

    @Bean
    public MongoClient mongoClient(){
        return MongoClients.create("mongodb://"+mongoHost+":"+mongoPort);
    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoClient(), mongoDB);
    }


}
