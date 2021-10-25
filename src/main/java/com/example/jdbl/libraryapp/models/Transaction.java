package com.example.jdbl.libraryapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String transactionId; //UUID

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private Student student;

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private Book book;

    @Enumerated(value= EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(value= EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    private Date transactionTime;

    private int fine;

}
