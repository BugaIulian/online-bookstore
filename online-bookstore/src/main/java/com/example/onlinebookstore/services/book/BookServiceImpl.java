package com.example.onlinebookstore.services.book;

import com.example.onlinebookstore.exceptions.book.BookNotFoundException;
import com.example.onlinebookstore.models.dto.BookDTO;
import com.example.onlinebookstore.models.entities.AuthorEntity;
import com.example.onlinebookstore.models.entities.BookEntity;
import com.example.onlinebookstore.models.entities.GenreEntity;
import com.example.onlinebookstore.repositories.AuthorRepository;
import com.example.onlinebookstore.repositories.BookRepository;
import com.example.onlinebookstore.repositories.GenreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ObjectMapper objectMapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, ObjectMapper objectMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity bookToSaveInDB = objectMapper.convertValue(bookDTO, BookEntity.class);
        checkAuthorDuplicates(bookDTO, bookToSaveInDB);
        BookEntity savedBookInDB = bookRepository.save(bookToSaveInDB);
        GenreEntity genreToBeSaved = objectMapper.convertValue(bookDTO.getGenre(),GenreEntity.class);
        genreRepository.save(genreToBeSaved);
        return objectMapper.convertValue(savedBookInDB, BookDTO.class);
    }


    @Override
    public List<BookDTO> getBooks() {
        try {
            List<BookEntity> libraryStock = bookRepository.findAll();
            List<BookDTO> libraryStockDTO = new ArrayList<>();
            libraryStock.forEach(bookEntity -> libraryStockDTO.add(objectMapper.convertValue(bookEntity, BookDTO.class)));
            return libraryStockDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving books", e);
        }
    }

    @Override
    public BookDTO updateBookById(Long id, BookDTO bookDTO) {
        BookEntity editedBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        updateBookFields(bookDTO, editedBook);
        BookEntity editedBookSaved = bookRepository.save(editedBook);
        return objectMapper.convertValue(editedBookSaved, BookDTO.class);
    }

    /** The method deleteBookById will first check if the book that will be erased has an author that has more than 1 book in the book
     * library stock, if it has 1 book the book will be erased together with the author from the author table, however if it has more than 1 book,
     * the numberOfBooks written will decrement by 1, this way the relation between books table and author table will be updated */

    @Override
    public void deleteBookById(Long id) {

        BookEntity targetBookInDb = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        int numberOfBooksWritten = targetBookInDb.getAuthor().getNumberOfBooks();
        AuthorEntity targetedBookAuthor = targetBookInDb.getAuthor();

        if (numberOfBooksWritten == 1) {
            bookRepository.deleteById(id);
        } else {
            targetedBookAuthor.setNumberOfBooks(numberOfBooksWritten - 1);
            authorRepository.save(targetedBookAuthor);
        }
    }

    /**
     * This method will check if there are any duplicates in the author table on the author_name column, if there are the id will remain the same,
     * if not, a new author entity will be created in the table, this is called from the addBook method.
     * This comment is subject to be erased later in the process of building the app.
     */

    private void checkAuthorDuplicates(BookDTO bookDTO, BookEntity bookToSaveInDB) {

        int numberOfBooksWritten;
        AuthorEntity author = authorRepository.findByName(bookDTO.getAuthor().getName());
        if (author == null) {
            author = new AuthorEntity();
            author.setName(bookDTO.getAuthor().getName());
            author.setNumberOfBooks(1);
            bookToSaveInDB.setAuthor(author);
        } else {
            numberOfBooksWritten = author.getNumberOfBooks();
            author.setNumberOfBooks(++numberOfBooksWritten);
        }
        bookToSaveInDB.setAuthor(author);
    }

    /**
     * This method will call the setters from the BookDTO and input new fields in case the admin wants to update specific books by id from the library.
     * This is called from the updateBook method.
     * This comment is subject to be erased later in the process of building the app.
     */

    private void updateBookFields(BookDTO bookDTO, BookEntity editedBook) {
        BookEntity authorBook = objectMapper.convertValue(bookDTO, BookEntity.class);
        editedBook.setTitle(bookDTO.getTitle());
        editedBook.setAuthor(authorBook.getAuthor());
        editedBook.setPublisher(bookDTO.getPublisher());
        editedBook.setPublicationDate(bookDTO.getPublicationDate());
        editedBook.setCodeISBN(bookDTO.getCodeISBN());
        editedBook.setGenre(bookDTO.getGenre());
        editedBook.setSynopsis(bookDTO.getSynopsis());
        editedBook.setCoverDesign(bookDTO.getCoverDesign());
        editedBook.setPageCount(bookDTO.getPageCount());
        editedBook.setLanguage(bookDTO.getLanguage());
        editedBook.setFormat(bookDTO.getFormat());
        editedBook.setPrice(bookDTO.getPrice());
        editedBook.setReview(bookDTO.getReview());
        editedBook.setQrCode(bookDTO.getQrCode());
        editedBook.setInventory(bookDTO.getInventory());
    }
}