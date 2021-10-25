package com.example.jdbl.libraryapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @OneToMany(mappedBy ="author" ) // One(Author) to Many(Books)
    @JsonIgnoreProperties(value="author")
    private List<Book> books;

    private String country;
    private int age;

}
