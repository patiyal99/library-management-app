package com.example.jdbl.libraryapp.services;

import com.example.jdbl.libraryapp.models.*;
import com.example.jdbl.libraryapp.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${books.max-allotment}")
    int maxAllotment;

    @Value("${books.allotted-time}")
    int daysAllotted;

    @Value("${books.fine-per-day}")
    int finePerDay;

    public String issueBook(int bookId,int studentId) throws Exception {

        //1.get book by id + student by id
        //2.check for availability
        //3,check for student's threshold
        //4.create transaction
        //5.make the book unavailable + increment 1 in student's allotted books

        Book book= bookService.getBookById(bookId);

        if(book==null || !book.isAvailable()){
            return "The requested book could not be found";
        }

        Student student=studentService.getStudent(studentId);

        if(student==null || student.getBooks().size()>=maxAllotment){
            return "Student could not be found or max number of books already issued";
        }

        Transaction transaction=Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .transactionStatus(TransactionStatus.PENDING)
                .student(student)
                .book(book)
                .build();

        try {
            transaction = transactionRepository.save(transaction);

            book.setStudent(student);
            book.setAvailable(false);

            bookService.addOrUpdateBook(book);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

            transactionRepository.save(transaction);
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Transaction with id"+transaction.getId()+" failed");
        }

        return transaction.getTransactionId();
    }

    public Integer getFine(int bookId,int studentId){
        /*
        * 1.Get issue date from transaction table for the particular studentId and bookId and also the
        * transaction type should be ISSUE sort by transaction date in desc order and get 1st
        * record, the status should be SUCCESS.
        * 2.Cal the no. of days extra- finer per day* delayed days.
        * */
        List<Transaction> transactionList=transactionRepository.getTransactions(studentId,bookId,TransactionStatus.SUCCESS,TransactionType.ISSUE);
        Transaction transaction=transactionList.get(transactionList.size()-1);

        //Logic to find the num of days passed from the issue date

        Date transactionDate= transaction.getTransactionTime();
        long timeInMillisPassed=System.currentTimeMillis() - transactionDate.getTime();
        Long daysPassed= TimeUnit.DAYS.convert(timeInMillisPassed,TimeUnit.MILLISECONDS); //converts time in millis to Days

        if(daysPassed>daysAllotted){
            return (daysPassed.intValue() - daysAllotted)*finePerDay;
        }

        return 0;
    }

    public String returnBook(int bookId,int studentId,int fine){
        /*
        * 1. if fine>0 then increment total fine in student record
        * 2. make book as available
        * 3. transaction
         */

        //Pre-condition: Book and student should be available and book should be assigned to student

        Book book= bookService.getBookById(bookId);

        if(book==null || book.isAvailable()){
            return "The requested book could not be found or  it's not issued to anyone";
        }

        Student student=studentService.getStudent(studentId);

        if(student==null || book.getStudent().getId()!=studentId){
            return "Student could not be found or book is not issued to that student";
        }

        Transaction transaction=Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .transactionStatus(TransactionStatus.PENDING)
                .student(student)
                .book(book)
                .fine(fine)
                .build();
        try {
            //Get fine for this transaction and reject if actual fine is greater than what is coming in request
            transaction = transactionRepository.save(transaction);

            book.setAvailable(true);
            book.setStudent(null);

            bookService.addOrUpdateBook(book);

            if (fine > 0) {
                student.setTotalFine(student.getTotalFine() + fine);
                studentService.addOrUpdateStudent(student);
            }
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);

        }catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
        }
        return transaction.getTransactionId();
    }
}
