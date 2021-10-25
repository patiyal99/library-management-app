package com.example.jdbl.libraryapp.services;

import com.example.jdbl.libraryapp.models.Author;
import com.example.jdbl.libraryapp.models.Book;
import com.example.jdbl.libraryapp.repositories.BookRepository;
import com.example.jdbl.libraryapp.requests.BookCreateRequest;
import com.example.jdbl.libraryapp.requests.BookUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookService {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepository bookRepository;

    @Transactional
    public void addBook(BookCreateRequest bookCreateRequest){

        //Author object -> save it in DB
        //Book Object + Link the authorId
        //Save the book object

        Author author=authorService.getAuthorByEmail(bookCreateRequest.getAuthorEmail());

        if(author==null){
            author= Author.builder()
                .name(bookCreateRequest.getAuthorName())
                .age(bookCreateRequest.getAuthorAge())
                .country(bookCreateRequest.getAuthorCountry())
                .email(bookCreateRequest.getAuthorEmail())
                .build();

            authorService.addAuthor(author);
        }

        Book book=Book.builder()
                .cost(bookCreateRequest.getCost())
                .name(bookCreateRequest.getName())
                .genre(bookCreateRequest.getGenre())
                .author(author)
                .available(true)
                .build();

        bookRepository.save(book);
    }

    public Book getBookById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    public void addOrUpdateBook(Book book){
        bookRepository.save(book);
    }

    public void updateBook(int bookId,BookUpdateRequest bookUpdateRequest){
        bookRepository.updateBook(bookUpdateRequest.getCost(), bookUpdateRequest.getGenre(),bookUpdateRequest.getName(),bookId);
    }


}
