package com.self.springeurekaclientstudentservice.ws;

import com.self.springeurekaclientstudentservice.dao.StudentRepository;
import com.self.springeurekaclientstudentservice.loader.StudentsDataLoader;
import com.self.springeurekaclientstudentservice.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentsDataLoader studentsDataLoader;


    @GetMapping("/students")
    public ResponseEntity<Page<Student>> getStudent(
                        @RequestParam(defaultValue = "l") Integer page,
                        @RequestParam(defaultValue = "3") Integer limit){
        if (limit == 0)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(studentRepository.findAll(PageRequest.of(page, limit)));
    }

    @PostMapping("/students/feed")
    public ResponseEntity<String> loadData(){
        boolean isLoadingFromFile=false;
        String loaderState = studentsDataLoader.loadStudents(isLoadingFromFile);
        return ResponseEntity.ok(loaderState);
    }

    @GetMapping("/students/{schoolName}")
    public ResponseEntity<List<Student>> getStudent(@PathVariable String schoolName){
        if (schoolName == null || ("").equals(schoolName))
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(studentRepository.findStudentBySchoolName(schoolName));
    }

    @PostMapping("/students")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        if(student == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentRepository.insert(student));
    }

    @GetMapping("students/{schoolName}/{name}")
    public ResponseEntity<List<Student>> getStudentByNameAndSchoolName(@PathVariable String schoolName, @PathVariable String name){
        return ResponseEntity.ok(studentRepository.findStudentByNameAndSchoolName(name, schoolName));
    }


}
