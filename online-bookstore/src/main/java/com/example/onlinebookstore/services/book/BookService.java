package com.example.onlinebookstore.services.book;

import com.example.onlinebookstore.models.dto.BookDTO;
import com.example.onlinebookstore.models.dto.UserDTO;

import java.util.List;

public interface BookService {

    BookDTO addBook(BookDTO bookDTO);

    List<BookDTO> getBooks();

    BookDTO updateBookById(Long id, BookDTO bookDTO);

    void deleteBookById(Long id);

    List<UserDTO> getUsers();
}