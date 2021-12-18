package com.example.jdbl.libraryapp.contollers;

import com.example.jdbl.libraryapp.models.User;
import com.example.jdbl.libraryapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/issue_book")
    public String issueBook(@RequestParam("book_id")int bookId) throws Exception {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        int studentId=user.getUserTypeId();

       return transactionService.issueBook(bookId,studentId);
    }

    //called by student only
    @GetMapping("/transaction/fine")
    public Integer getFine(@RequestParam("book_id")int bookId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        int studentId=user.getUserTypeId();

        return transactionService.getFine(bookId,studentId);
    }

    //called by admin only
    @GetMapping("/transaction/fine/student_id/{id}")
    public Integer getFine(@RequestParam("book_id")int bookId,@RequestParam("student_id")int studentId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();

        return transactionService.getFine(bookId,studentId);
    }

    @PostMapping("/transaction/return_book")
    public String returnBook(@RequestParam("book_id")int bookId,
                             @RequestParam("fine")int fine){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        int studentId=user.getUserTypeId();

        return transactionService.returnBook(bookId,studentId,fine);
    }
} 
