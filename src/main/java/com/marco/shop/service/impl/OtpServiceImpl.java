package com.marco.shop.service.impl;

import com.marco.shop.service.RedisService;
import com.marco.shop.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    private RedisService<String, Object> redisService;

    @Value("${otp.expire.second}")
    private Long OTP_EXPIRE_SECOND;

    @Override
    public String generateOTP(String key) {
        Random random = new Random();
        String OTP = String.valueOf(100000 + random.nextInt(900000));

        redisService.set(key, OTP, OTP_EXPIRE_SECOND);

        return OTP;
    }

    @Override
    public String getOtpByKey(String key) {
        Object otp = redisService.get(key);

        return (String) otp;
    }

    @Override
    public void clearOtpByKey(String key) {
        redisService.del(key);
    }


}
