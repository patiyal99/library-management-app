package com.example.jdbl.libraryapp.repositories;

import com.example.jdbl.libraryapp.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {

}
