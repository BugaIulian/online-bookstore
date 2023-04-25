package group1.itschool.onlinebookstore.controllers;

import group1.itschool.onlinebookstore.models.dto.BookDTO;
import group1.itschool.onlinebookstore.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("admin/books")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {

        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }
}