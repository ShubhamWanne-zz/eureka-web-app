package com.self.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee{
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private Date dateOfJoining;
    private Long salary;
    private double lastHike;
    private String phoneNumber;
    private String country;
    private String city;
    private String state;
    private String zip;

}
