package com.example.jdbl.libraryapp.repositories;

import com.example.jdbl.libraryapp.models.Transaction;
import com.example.jdbl.libraryapp.models.TransactionStatus;
import com.example.jdbl.libraryapp.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Query("select t from Transaction t where t.student.id = :studentId and t.book.id= :bookId " +
            "and t.transactionStatus = :status and t.transactionType = " +
            ":transactionType order by t.transactionTime")
    List<Transaction> getTransactions(int studentId, int bookId, TransactionStatus status,
                                      TransactionType transactionType);

}
