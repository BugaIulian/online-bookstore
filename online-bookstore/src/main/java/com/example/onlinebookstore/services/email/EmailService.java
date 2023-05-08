package com.example.onlinebookstore.services.email;

public interface EmailService {

    void sendRegistrationEmail(String newUserEmail, String username);

    void sendOrderEmail(String userEmail, String username);
}