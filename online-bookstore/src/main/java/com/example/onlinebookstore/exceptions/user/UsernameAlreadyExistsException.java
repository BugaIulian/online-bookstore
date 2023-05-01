package com.example.onlinebookstore.exceptions.user;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String usernameDuplicate) {
        super(usernameDuplicate);
    }
}