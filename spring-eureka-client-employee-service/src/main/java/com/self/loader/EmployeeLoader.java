package com.self.loader;

import com.self.dao.EmployeeRepository;
import com.self.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeeLoader {

    enum Status{
        SUCCESSFUL,
        FAILED
    };

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MongoOperations mongoOperations;

    public String load(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/employee.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Status.FAILED.name();
        }
        String line="";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        boolean isHeader=true;
        List<Employee> employees = new ArrayList<>();
        int chunkSize=1000;
        while (true) {
            try {
                if (!((line = br.readLine())!=null)) break;
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] arr = line.split(",");
                Employee employee = Employee.builder()
                        .id(Long.parseLong(arr[0]))
                        .firstName((arr[2]).trim())
                        .lastName(arr[4].trim())
                        .email(arr[6].trim())
                        .dateOfBirth(sdf.parse(arr[10].trim()))
                        .dateOfJoining(sdf.parse(arr[14].trim()))
                        .salary(Long.parseLong(arr[25].trim()))
                        .lastHike(Double.parseDouble(arr[26].trim().substring(0, arr[26].length()-1)))
                        .phoneNumber(arr[28].trim())
                        .country(arr[30].trim())
                        .city(arr[31].trim())
                        .state(arr[32].trim())
                        .zip(arr[33].trim())
                        .build();
                employees.add(employee);
                if (employees.size() == chunkSize){
                    employeeRepository.saveAll(employees);
                    System.out.println("Employee count "+ employees.size()+" has been written successfully");
                    employees.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return Status.FAILED.name();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Loading status:  "+ Status.SUCCESSFUL.name());
        return Status.SUCCESSFUL.name();
    }

}
