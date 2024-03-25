package com.marco.mcshop.service.impl;

import com.marco.mcshop.config.properties.EmailProperties;
import com.marco.mcshop.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final EmailProperties emailProperties;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, EmailProperties emailProperties) {
        this.emailSender = emailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    @Async
    public void sendSimpleMessage(String receiver, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailProperties.getUsername());
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(text);

        log.info("Sending simple message:{} with subject:{} to receiver:{}", text, subject, receiver);

        emailSender.send(message);
    }
}
