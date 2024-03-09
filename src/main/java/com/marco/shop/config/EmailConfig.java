package com.marco.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${mcshop.mail.username}")
    private String username;
    @Value("${mcshop.mail.password}")
    private String password;
    @Value("${mcshop.mail.port}")
    private int port;
    @Value("${mcshop.mail.host}")
    private String host;
    @Value("${mcshop.mail.properties.mail.smtp.starttls.enable}")
    private String enable;
    @Value("${mcshop.mail.properties.mail.smtp.auth}")
    private String auth;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enable);
        props.put("mail.debug", "true");

        return mailSender;
    }
}
