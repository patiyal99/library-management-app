package com.example.jdbl.libraryapp.contollers;

import com.example.jdbl.libraryapp.models.Student;
import com.example.jdbl.libraryapp.models.User;
import com.example.jdbl.libraryapp.requests.StudentCreateRequest;
import com.example.jdbl.libraryapp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/student")
    public void addStudent(@RequestBody StudentCreateRequest studentCreateRequest){
        studentService.addStudent(studentCreateRequest);
    }

    //It should be called by student
    @GetMapping("/student")
    public Student getStudent(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        int studentId=user.getUserTypeId();

        return studentService.getStudent(studentId);
    }

    //It should be called by the Admin
    @GetMapping("/student/id/{id}")
    public Student getStudentById(@PathVariable("id")int id){
        return studentService.getStudent(id);
    }
}
