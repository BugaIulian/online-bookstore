package group1.itschool.onlinebookstore.exceptions.book;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}