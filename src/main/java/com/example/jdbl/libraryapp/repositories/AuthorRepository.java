package com.example.jdbl.libraryapp.repositories;

import com.example.jdbl.libraryapp.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

    Author findByEmail(String email);
}
