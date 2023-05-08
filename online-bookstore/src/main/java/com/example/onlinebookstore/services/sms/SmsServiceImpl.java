package com.example.onlinebookstore.services.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SmsServiceImpl implements SmsService {

    @Value("${account.sid}")
    private String accountSid;
    @Value("${auth.token}")
    private String authToken;
    @Value("${phone.number}")
    private String fromPhoneNumber;

    @Override
    public void sendSMSOrder(String username, String userPhoneNumber, String productName, String author) {

        Twilio.init(accountSid, authToken);
        Message smsMessage = Message.creator(
                new PhoneNumber("+40756612594"),
                new PhoneNumber(fromPhoneNumber),
                "Thank you, " + username + ". Your book, " + productName + " by " + author + ", it's on your way. Expect delivery on " + LocalDate.now().plusDays(10)).create();
    }
}