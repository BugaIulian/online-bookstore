package com.example.onlinebookstore.services.sms;

public interface SmsService {

    void sendSMSOrder(String username, String phoneNumber, String productName, String authorName);
}