package com.example.onlinebookstore.exceptions.sendgrid;

public class SendGridAPINotFoundException extends RuntimeException {

    public SendGridAPINotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
