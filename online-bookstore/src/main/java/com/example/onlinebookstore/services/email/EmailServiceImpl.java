package com.example.onlinebookstore.services.email;

import com.example.onlinebookstore.configs.SendGridConfiguration;
import com.example.onlinebookstore.exceptions.sendgrid.SendGridAPINotFoundException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendRegistrationEmail(String newUserEmail) {

        Mail mail = setEmailToAndFrom(newUserEmail);
        SendGrid sg = getSendGridApi();
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Mail setEmailToAndFrom(String newUserEmail) {
        Email from = new Email("iulian.ionut.buga@gmail.com");
        String subject = "Sending with Sendgrid is fun";
        Email to = new Email(newUserEmail);
        Content content = new Content("text/plain", "and easy to do it everywhere");
        return new Mail(from, subject, to, content);
    }

    private static SendGrid getSendGridApi() {
        SendGridConfiguration configSendGrid;
        try {
            configSendGrid = new SendGridConfiguration();
        } catch (ConfigurationException e) {
            throw new SendGridAPINotFoundException("No API defined in the application.properties file");
        }
        return new SendGrid(configSendGrid.getStringAPIKey());
    }
}