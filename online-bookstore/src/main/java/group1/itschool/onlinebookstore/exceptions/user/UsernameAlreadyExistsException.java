package group1.itschool.onlinebookstore.exceptions.user;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String usernameDuplicate) {
        super(usernameDuplicate);
    }
}