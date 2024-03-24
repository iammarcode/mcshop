package com.marco.mcshop.service;

public interface OtpService {
    String generateOTP(String key);

    String getOtpByKey(String key);

    void clearOtpByKey(String key);
}
