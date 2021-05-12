package com.self.springeurekaclientstudentservice.config;

import com.self.springeurekaclientstudentservice.loader.StudentsDataLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfiguration {
    @Bean
    public StudentsDataLoader studentsDataLoader(){
        return new StudentsDataLoader();
    }
}
