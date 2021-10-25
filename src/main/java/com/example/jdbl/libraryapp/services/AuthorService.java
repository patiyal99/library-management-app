package com.example.jdbl.libraryapp.services;

import com.example.jdbl.libraryapp.models.Author;
import com.example.jdbl.libraryapp.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Author getAuthorByEmail(String email){
        return authorRepository.findByEmail(email);
    }

    public Author addAuthor(Author author){
        return authorRepository.save(author);
    }
}
