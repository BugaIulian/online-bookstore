package com.example.onlinebookstore.exceptions.user;

public class UserCreationException extends RuntimeException {

    public UserCreationException(String usernameDuplicate) {
        super(usernameDuplicate);
    }
}