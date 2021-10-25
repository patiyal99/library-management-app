package com.example.jdbl.libraryapp.contollers;

import com.example.jdbl.libraryapp.models.Book;
import com.example.jdbl.libraryapp.requests.BookCreateRequest;
import com.example.jdbl.libraryapp.requests.BookUpdateRequest;
import com.example.jdbl.libraryapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public void createBook(@RequestBody BookCreateRequest bookCreateRequest) {

        bookService.addBook(bookCreateRequest);
    }

    @GetMapping("/book")
    public Book getBook(@RequestParam("id") int id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/book")
    public void updateBook(@RequestParam("book_id") int bookId, @RequestBody BookUpdateRequest bookUpdateRequest) {
        bookService.updateBook(bookId,bookUpdateRequest);
    }
}

