package group1.itschool.onlinebookstore.services.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import group1.itschool.onlinebookstore.models.dto.BookDTO;
import group1.itschool.onlinebookstore.models.entities.AuthorEntity;
import group1.itschool.onlinebookstore.models.entities.BookEntity;
import group1.itschool.onlinebookstore.repositories.AuthorRepository;
import group1.itschool.onlinebookstore.repositories.BookRepository;
import group1.itschool.onlinebookstore.util.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ObjectMapper objectMapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, ObjectMapper objectMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity bookToSaveInDB = objectMapper.convertValue(bookDTO, BookEntity.class);
        checkAuthorDuplicates(bookDTO, bookToSaveInDB);
        BookEntity savedBookInDB = bookRepository.save(bookToSaveInDB);
        return objectMapper.convertValue(savedBookInDB, BookDTO.class);
    }


    @Override
    public List<BookDTO> getBooks() {
        List<BookEntity> libraryStock = bookRepository.findAll();
        List<BookDTO> libraryStockDTO = new ArrayList<>();
        libraryStock.forEach(bookEntity -> {
            libraryStockDTO.add(objectMapper.convertValue(bookEntity, BookDTO.class));
        });
        return libraryStockDTO;
    }

    @Override
    public BookDTO updateBookById(Long id, BookDTO bookDTO) {
        BookEntity editedBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        updateBookFields(bookDTO, editedBook);
        BookEntity editedBookSaved = bookRepository.save(editedBook);
        return objectMapper.convertValue(editedBookSaved, BookDTO.class);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * This method will check if there are any duplicates in the author table on the author_name column, if there are the id will remain the same,
     * if not, a new author entity will be created in the table, this is called from the addBook method.
     * This comment is subject to be erased later in the process of building the app.
     */

    private void checkAuthorDuplicates(BookDTO bookDTO, BookEntity bookToSaveInDB) {
        AuthorEntity author = authorRepository.findByName(bookDTO.getAuthor().getName());
        if (author == null) {
            author = new AuthorEntity();
            author.setName(bookDTO.getAuthor().getName());
            bookToSaveInDB.setAuthor(author);
        }
        bookToSaveInDB.setAuthor(author);
    }


    /**
     * This method will call the setters from the BookDTO and input new fields in case the admin wants to update specific books by id from the library.
     * This is called from the updateBook method.
     * This comment is subject to be erased later in the process of building the app.
     */

    private void updateBookFields(BookDTO bookDTO, BookEntity editedBook) {
        editedBook.setTitle(bookDTO.getTitle());
        //editedBook.setAuthor(bookDTO.getAuthor());
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