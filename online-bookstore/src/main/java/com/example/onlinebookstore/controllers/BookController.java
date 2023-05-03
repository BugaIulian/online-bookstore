package com.example.onlinebookstore.controllers;

import com.example.onlinebookstore.models.dto.BookDTO;
import com.example.onlinebookstore.services.book.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("admin/books")
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @GetMapping("admin/books")
    public List<BookDTO> getLibraryStock() {
        return bookService.getBooks();
    }

    @PutMapping("admin/books/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBookById(id, bookDTO));
    }

    @DeleteMapping("admin/books/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }
}