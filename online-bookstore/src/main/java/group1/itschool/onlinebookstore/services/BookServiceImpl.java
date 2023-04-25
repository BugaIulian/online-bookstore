package group1.itschool.onlinebookstore.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import group1.itschool.onlinebookstore.models.dto.BookDTO;
import group1.itschool.onlinebookstore.models.entities.BookEntity;
import group1.itschool.onlinebookstore.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    public BookServiceImpl(BookRepository bookRepository, ObjectMapper objectMapper) {
        this.bookRepository = bookRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity bookToSaveInDB = objectMapper.convertValue(bookDTO, BookEntity.class);
        BookEntity savedBookInDB = bookRepository.save(bookToSaveInDB);
        return objectMapper.convertValue(savedBookInDB,BookDTO.class);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        return null;
    }

    @Override
    public List<BookDTO> getBooks() {
        return null;
    }
}