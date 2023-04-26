package group1.itschool.onlinebookstore.controllers;

import group1.itschool.onlinebookstore.models.dto.BookDTO;
import group1.itschool.onlinebookstore.services.book.BookService;
import jakarta.validation.Valid;
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
        return ResponseEntity.ok(bookService.addBook(bookDTO));
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
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }
}