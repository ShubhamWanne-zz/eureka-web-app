package com.self.springeurekaclientstudentservice.dao;

import com.self.springeurekaclientstudentservice.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, Long> {
    List<Student> findStudentBySchoolName(String schoolName);
    List<Student> findStudentByNameAndSchoolName(String name, String schoolName);
}
