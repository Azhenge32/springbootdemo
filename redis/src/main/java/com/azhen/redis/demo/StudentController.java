package com.azhen.redis.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentRepository studentRepository;

    @RequestMapping("/save")
    public void save() {
        Student student = new Student(
                "Eng2015002", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);
    }

    @RequestMapping("/update")
    public void update() {
        Student retrievedStudent =
                studentRepository.findById("Eng2015001").get();
        retrievedStudent.setName("Richard Watson");
        studentRepository.save(retrievedStudent);
    }

    @RequestMapping("/findAll")
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }
}
