package com.example.onlinebookstore.services.book;

import com.example.onlinebookstore.exceptions.book.BookNotFoundException;
import com.example.onlinebookstore.exceptions.book.BooksDatabaseNotFoundException;
import com.example.onlinebookstore.exceptions.book.ISBNDuplicateException;
import com.example.onlinebookstore.models.dto.BookDTO;
import com.example.onlinebookstore.models.entities.AuthorEntity;
import com.example.onlinebookstore.models.entities.BookEntity;
import com.example.onlinebookstore.models.entities.GenreEntity;
import com.example.onlinebookstore.repositories.AuthorRepository;
import com.example.onlinebookstore.repositories.BookRepository;
import com.example.onlinebookstore.repositories.GenreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Transactional
    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity bookToSaveInDB = objectMapper.convertValue(bookDTO, BookEntity.class);
        checkAuthorDuplicates(bookDTO, bookToSaveInDB);
        checkISBN(bookDTO);
        GenreEntity genreToBeSaved = objectMapper.convertValue(bookDTO.getGenre(), GenreEntity.class);
        genreToBeSaved = checkIfGenreExistsInDB(genreToBeSaved);
        Set<GenreEntity> genres = bookToSaveInDB.getGenres();
        genres.add(genreToBeSaved);
        bookToSaveInDB.setGenres(genres);
        bookRepository.save(bookToSaveInDB);
        return objectMapper.convertValue(bookToSaveInDB, BookDTO.class);
    }

    @Override
    public List<BookDTO> getBooks() {
        try {
            List<BookEntity> libraryStock = bookRepository.findAll();
            List<BookDTO> libraryStockDTO = new ArrayList<>();
            libraryStock.forEach(bookEntity -> libraryStockDTO.add(objectMapper.convertValue(bookEntity, BookDTO.class)));
            return libraryStockDTO;
        } catch (Exception e) {
            throw new BooksDatabaseNotFoundException("Error while retrieving books from database.");
        }
    }

    @Override
    @Transactional
    public BookDTO updateBookById(Long id, BookDTO bookDTO) {
        BookEntity editedBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        updateBookFields(bookDTO, editedBook);
        GenreEntity genreToBeSaved = objectMapper.convertValue(bookDTO.getGenre(), GenreEntity.class);
        genreToBeSaved = checkIfGenreExistsInDB(genreToBeSaved);
        Set<GenreEntity> genres = editedBook.getGenres();
        genres.add(genreToBeSaved);
        editedBook.setGenres(genres);
        return objectMapper.convertValue(editedBook, BookDTO.class);
    }

    @Override
    @Transactional
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

    @Override
    public BookDTO getBooksByTitle(String title) {
        BookEntity targetBook = bookRepository.findBookByTitle(title);
        return objectMapper.convertValue(targetBook, BookDTO.class);
    }

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

    private void updateBookFields(BookDTO bookDTO, BookEntity editedBook) {

        editedBook.setTitle(bookDTO.getTitle());
        checkAuthorDuplicates(bookDTO, editedBook);
        editedBook.setPublisher(bookDTO.getPublisher());
        editedBook.setPublicationDate(bookDTO.getPublicationDate());
        editedBook.setCodeISBN(bookDTO.getCodeISBN());
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

    private void checkISBN(BookDTO bookDTO) {
        List<BookEntity> books = bookRepository.findAll();
        for (BookEntity book : books) {
            if (bookDTO.getCodeISBN().equals(book.getCodeISBN())) {
                throw new ISBNDuplicateException("Book has the same isbn code");
            }
        }
    }

    private GenreEntity checkIfGenreExistsInDB(GenreEntity genreToBeSaved) {
        List<GenreEntity> genreList = genreRepository.findAll();
        boolean genreAlreadyExists = false;
        for (GenreEntity genre : genreList) {
            if (genre.getName().equals(genreToBeSaved.getName())) {
                genreAlreadyExists = true;
                genreToBeSaved = genre;
                break;
            }
        }
        if (!genreAlreadyExists) {
            genreRepository.save(genreToBeSaved);
        }
        return genreToBeSaved;
    }
}