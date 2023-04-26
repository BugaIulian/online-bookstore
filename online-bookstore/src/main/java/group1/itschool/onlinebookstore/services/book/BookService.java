package group1.itschool.onlinebookstore.services.book;

import group1.itschool.onlinebookstore.models.dto.BookDTO;
import java.util.List;

public interface BookService {

    BookDTO addBook(BookDTO bookDTO);

    List<BookDTO> getBooks();

    BookDTO updateBookById(Long id, BookDTO bookDTO);

    void deleteBookById(Long id);
}