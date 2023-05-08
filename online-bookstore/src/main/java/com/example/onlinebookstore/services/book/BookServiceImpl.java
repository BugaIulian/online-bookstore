package com.example.onlinebookstore.services.book;

import com.example.onlinebookstore.exceptions.book.BookNotFoundException;
import com.example.onlinebookstore.models.dto.BookDTO;
import com.example.onlinebookstore.models.dto.UserDTO;
import com.example.onlinebookstore.models.entities.AuthorEntity;
import com.example.onlinebookstore.models.entities.BookEntity;
import com.example.onlinebookstore.models.entities.GenreEntity;
import com.example.onlinebookstore.models.entities.UserEntity;
import com.example.onlinebookstore.repositories.AuthorRepository;
import com.example.onlinebookstore.repositories.BookRepository;
import com.example.onlinebookstore.repositories.GenreRepository;
import com.example.onlinebookstore.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, ObjectMapper objectMapper, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity bookToSaveInDB = objectMapper.convertValue(bookDTO, BookEntity.class);
        checkAuthorDuplicates(bookDTO, bookToSaveInDB);
        BookEntity savedBookInDB = bookRepository.save(bookToSaveInDB);

        GenreEntity genreToBeSaved = objectMapper.convertValue(bookDTO.getGenre(), GenreEntity.class);
        genreToBeSaved = checkIfGenreExistsInDBAndMerge(genreToBeSaved);

        Set<GenreEntity> genres = savedBookInDB.getGenres();
        genres.add(genreToBeSaved);
        savedBookInDB.setGenres(genres);
        bookRepository.save(savedBookInDB);
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

    @Override
    public List<UserDTO> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        userEntities.forEach(userEntity -> userDTOS.add(objectMapper.convertValue(userEntity, UserDTO.class)));
        return userDTOS;
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
        //editedBook.setGenre(bookDTO.getGenre());
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

    private GenreEntity checkIfGenreExistsInDBAndMerge(GenreEntity genreToBeSaved) {
        List<GenreEntity> iterateRepositoryList = genreRepository.findAll();
        boolean genreAlreadyExists = false;
        for (GenreEntity genre : iterateRepositoryList) {
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