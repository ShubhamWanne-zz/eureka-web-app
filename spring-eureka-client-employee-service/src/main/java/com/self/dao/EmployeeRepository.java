package com.self.dao;

import com.self.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, Long> {
    public List<Employee> findByStateAndCityOrderBySalaryDesc(String state, String city);
    public List<Employee> findByStateAndCityOrderBySalary(String state, String city);
    public List<Employee> findByStateAndCityOrderByLastHikeDesc(String state, String city);
    public List<Employee> findByStateAndCityOrderByLastHike(String state, String city);
}
