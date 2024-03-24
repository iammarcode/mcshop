package com.marco.mcshop.exception.address;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long addressId) {
        super("Address not found with ID: " + addressId);
    }

}
