package com.marcoecommerce.shop.service;

import org.springframework.stereotype.Service;

import java.util.Random;

public interface OtpService {
    String generateOTP(String key);

    String getOtpByKey(String key);

    void clearOtpByKey(String key);
}
