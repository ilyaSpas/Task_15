package org.example.jdbc.controller;

import org.example.jdbc.entity.Book;
import org.example.jdbc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(bookService.get(id),
                HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAll(),
                HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.save(book),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.update(id, book),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return HttpStatus.OK;
    }
}
