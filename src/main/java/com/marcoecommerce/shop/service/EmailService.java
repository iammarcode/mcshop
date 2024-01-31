package com.marcoecommerce.shop.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
