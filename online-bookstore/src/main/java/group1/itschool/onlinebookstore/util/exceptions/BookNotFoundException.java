package group1.itschool.onlinebookstore.util.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}