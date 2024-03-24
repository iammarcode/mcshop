package com.marco.mcshop.exception.customer;

public class CustomerAlreadyExistException extends RuntimeException {
    public CustomerAlreadyExistException(String email) {
        super("Customer already exist with email: " + email);
    }
}
