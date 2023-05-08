package com.example.onlinebookstore.exceptions.book;

public class BooksDatabaseNotFoundException extends RuntimeException {
    public BooksDatabaseNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}