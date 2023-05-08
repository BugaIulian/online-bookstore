package com.example.onlinebookstore.exceptions.book;

public class ISBNDuplicateException extends RuntimeException {
    public ISBNDuplicateException(String errorMessage) {
        super(errorMessage);
    }
}