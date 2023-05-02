package com.example.crudApp.controller;

import com.example.crudApp.advice.Exceptionhandler;
import com.example.crudApp.dto.BookDTO;
import com.example.crudApp.model.Book;
import com.example.crudApp.repo.Bookrepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Validated
public class BookController {
    @Autowired
    public Bookrepo bookrepo;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks(){
        try {
            List<Book> bookList = new ArrayList<>(bookrepo.findAll());
            if(bookList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList,HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> bookData = bookrepo.findById(id);

        return bookData.map(book -> new ResponseEntity<>(book, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAllBooksauthor")
    public ResponseEntity<List<BookDTO>> getAllBooksauthor() {
        try {
            List<Book> bookList = bookrepo.findAll();
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<BookDTO> bookDTOList = bookList.stream()
                    .map(book -> new BookDTO(book.getAuthor()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(bookDTOList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getauthById/{id}")
    public ResponseEntity<BookDTO> getauthById(@PathVariable Long id) {
        Optional<Book> bookData = bookrepo.findById(id);
        return bookData.map(book -> new ResponseEntity<>(new BookDTO(book.getAuthor()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@Validated @RequestBody @NotNull Book book, BindingResult result) throws Exceptionhandler.InvalidtitleException, Exceptionhandler.InvalidAuthorException {

        String author = book.getAuthor();
        if(author == null || author.isBlank()) {
            throw new Exceptionhandler.InvalidAuthorException("author cannot be blank.");
        }

        for(char ch : author.toCharArray()) {
            if(Character.isDigit(ch)) {
                throw new Exceptionhandler.InvalidAuthorException("Author name should not contain numbers.");
            }
        }

        String title = book.getTitle();
        if(title == null || title.isBlank()) {
            throw new Exceptionhandler.InvalidtitleException("Title cannot be blank.");
        }
        if(bookrepo.existsByAuthorAndTitle(author, title)){
            throw new Exceptionhandler.InvalidAuthorException("Book with the same author and title already exists");
        }
        else if(bookrepo.existsByauthor(author)){
            throw new Exceptionhandler.InvalidAuthorException("Book with the same author already exists");
        }
        else if(bookrepo.existsBytitle(title)){
            throw new Exceptionhandler.InvalidAuthorException("Book with the same title already exists");
        }
        int numPages = book.getPages();
        double cost = numPages <= 1000 ? 100 : 100 + (numPages - 1000) * 0.5;

        book.setCost(cost);

        Book bookObj = bookrepo.save(book);
        return new ResponseEntity<>(bookObj, HttpStatus.OK);
    }

    @PutMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData){
        Optional<Book> oldBookData = bookrepo.findById(id);
        if(oldBookData.isPresent()){
            Book updatedBookData = oldBookData.get();

            updatedBookData.setTitle(newBookData.getTitle());
            updatedBookData.setAuthor(newBookData.getAuthor());
            Book BookObj = bookrepo.save(updatedBookData);
            return new ResponseEntity<>(BookObj,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id){
        bookrepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}