package com.self.springeurekaclientstudentservice.loader;

import com.self.springeurekaclientstudentservice.dao.StudentRepository;
import com.self.springeurekaclientstudentservice.model.Student;
import org.apache.commons.lang.math.IntRange;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class StudentsDataLoader {
    private static Logger LOGGER = Logger.getLogger(StudentsDataLoader.class.getName());
    enum LoaderState{
        SUCCESSFULL,
        FAILED
    }
    @Autowired
    StudentRepository studentRepository;

    public String loadStudents(boolean isFileAvailable) {
        if (!isFileAvailable){
            loadStudentsRandomly();
            return LoaderState.SUCCESSFULL.name();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/input.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return LoaderState.FAILED.name();
        }
        String line="";
        boolean isHeader=true;
        while (true) {
            try {
                if (!((line = br.readLine())!=null)) break;
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] arr = line.split(",");
                long id = Long.parseLong(new Random().nextInt(10000) + arr[0]);
                String name = arr[1].split(" ")[0];
                String schoolName = arr[3].trim();
                Student student = new Student(id,  name, schoolName);
                System.out.println("Inserting "+ student +" ...");
                studentRepository.insert(student);
            } catch (IOException e) {
                e.printStackTrace();
                return LoaderState.FAILED.name();
            }
        }
        return LoaderState.SUCCESSFULL.name();
    }

    private void loadStudentsRandomly() {
        List<Student> students = new ArrayList<>();
        String[] schools = {"VNIT", "MANIT", "WNIT", "KNIT"};
        IntStream.range(1,101).forEach(i->{
            students.add(new Student(new Random().nextLong(), "StudentName"+i, schools[new Random().nextInt(4)]));
        });
        studentRepository.insert(students);
    }

}
