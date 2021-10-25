package com.example.jdbl.libraryapp.requests;

import com.example.jdbl.libraryapp.models.Student;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreateRequest {


    private String name;
    private int age;
    private String country;
    private String contact;
    private String email;
    private String rollNo;

    public Student toStudent(){
        return Student.builder()
                .name(name)
                .age(age)
                .country(country)
                .email(email)
                .rollNo(rollNo)
                .contact(contact)
                .build();
    }
}
