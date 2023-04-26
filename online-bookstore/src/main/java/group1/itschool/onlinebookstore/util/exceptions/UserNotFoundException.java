package group1.itschool.onlinebookstore.util.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}