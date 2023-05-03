package com.example.onlinebookstore.services.email;

import com.example.onlinebookstore.configs.SendGridConfiguration;
import com.example.onlinebookstore.exceptions.sendgrid.SendGridAPINotFoundException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${email.from}")
    private String fromEmail;

    @Override
    public void sendRegistrationEmail(String newUserEmail, String username) {

        Mail mail = setEmailToAndFrom(newUserEmail, username);
        SendGrid sg = getSendGridApi();
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info(String.valueOf(response.getStatusCode()));
            log.info(response.getBody());
            log.info(response.getHeaders().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendOrderEmail(String userEmail, String username) {
        // TODO document why this method is empty
    }

    private Mail setEmailToAndFrom(String newUserEmail, String username) {

        Email from = new Email(fromEmail);
        Email to = new Email(newUserEmail);
        Mail mail = new Mail();
        Personalization personalization = new Personalization();
        personalization.addTo(to);
        personalization.addDynamicTemplateData("dynamic_content", username);
        mail.setFrom(from);
        mail.setTemplateId("d-820bf308c3c942d881870d01f95dff39");
        mail.addPersonalization(personalization);

        return mail;
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