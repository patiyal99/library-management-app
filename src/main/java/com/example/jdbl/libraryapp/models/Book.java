package com.example.jdbl.libraryapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(value= EnumType.STRING)
    private Genre genre;

    private int cost;
    private boolean available;

    @CreationTimestamp
    private Date bookAddedOn;

    @JoinColumn
    @ManyToOne //Many(Book) to One(Author)
    @JsonIgnoreProperties(value="books")
    private Author author; //Foreign Key

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("books")
    private Student student;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties("books")
    private List<Transaction> transactions;
}
