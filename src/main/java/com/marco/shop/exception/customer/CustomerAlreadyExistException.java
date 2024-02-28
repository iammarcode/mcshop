package com.marco.shop.exception.customer;

public class CustomerAlreadyExistException extends RuntimeException {
    public CustomerAlreadyExistException(String email) {
        super("Customer already exist with email: " + email);
    }
}
