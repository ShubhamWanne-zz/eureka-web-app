package com.self.ws;

import com.self.dao.EmployeeRepository;
import com.self.loader.EmployeeLoader;
import com.self.model.Employee;
import com.self.model.FieldsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeLoader employeeLoader;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/employee")
    public ResponseEntity<String> loadEmployeeData(){
        String status = employeeLoader.load();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/employee/{state}/{city}")
    public ResponseEntity<List> getEmployeesByStateAndCity(@PathVariable String state, @PathVariable String city,
                                                            @RequestParam(defaultValue = "Salary") String sortBy,
                                                           @RequestParam(defaultValue = "Asc") String order){
        Query query = new Query();
        query.addCriteria(Criteria.where("state").is(state).and("city").is(city));
        query.with(Sort.by(Sort.Direction.valueOf(order.toUpperCase()), sortBy));
        System.out.println(query.getQueryObject().toJson());
        return ResponseEntity.ok(mongoTemplate.find(query, Employee.class));
    }

    @GetMapping("/employee")
    public ResponseEntity<List> getEmployees(@RequestBody(required = false) FieldsWrapper fields, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit){
        Query query =  new Query().with(PageRequest.of(page, limit));
        query.fields().include(fields.getFields().stream().toArray(String[]::new)).exclude("id");
        return ResponseEntity.ok(mongoTemplate.find(query, Employee.class));
    }

    @GetMapping("/employee/salaries")
    public ResponseEntity<List> getEmployeeWithinSalaryRange(@RequestParam long from, @RequestParam long to){
        Query query = new Query();
        query.addCriteria(Criteria.where("salary").gt(from).lte(to));
        query.with(Sort.by(Sort.Direction.DESC, "salary"));
        query.with(PageRequest.of(0,100));
        return ResponseEntity.ok(mongoTemplate.find(query, Employee.class));
    }


}
