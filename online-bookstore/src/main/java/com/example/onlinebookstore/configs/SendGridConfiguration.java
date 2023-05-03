package com.example.onlinebookstore.configs;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
@ConfigurationProperties
public class SendGridConfiguration {

    private final Configurations configs = new Configurations();
    private final Configuration sendgridConfig = configs.properties(new File("application.properties"));

    public String getStringAPIKey() {
        return sendgridConfig.getString("sendgrid.api.key");
    }

    public SendGridConfiguration() throws ConfigurationException {
        // TODO document why this constructor is empty
    }
}