package com.example.onlinebookstore.exceptions.user;

public class UsersCredentialsExceptions extends RuntimeException{
    public UsersCredentialsExceptions(String errorMessage) {
        super(errorMessage);
    }
}
