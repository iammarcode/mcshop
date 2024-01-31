package com.marcoecommerce.shop.exception.auth;

public class OtpValidationFailedException extends RuntimeException {
    public OtpValidationFailedException(String otp) {
        super("Otp validation failed: " + otp);
    }
}
