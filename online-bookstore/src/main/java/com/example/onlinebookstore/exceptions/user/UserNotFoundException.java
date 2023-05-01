package com.example.onlinebookstore.exceptions.user;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}