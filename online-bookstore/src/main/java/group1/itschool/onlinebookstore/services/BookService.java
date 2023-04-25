package group1.itschool.onlinebookstore.services;

import group1.itschool.onlinebookstore.models.dto.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO addBook(BookDTO bookDTO);

    BookDTO updateBook(BookDTO bookDTO);

    List<BookDTO> getBooks();
}