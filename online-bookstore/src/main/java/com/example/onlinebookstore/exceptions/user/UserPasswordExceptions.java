package com.example.onlinebookstore.exceptions.user;

public class UserPasswordExceptions extends RuntimeException{
    public UserPasswordExceptions(String errorMessage) {
        super(errorMessage);
    }
}
