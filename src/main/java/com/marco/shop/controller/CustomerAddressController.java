package com.marco.shop.controller;

import com.marco.shop.model.customer.CustomerDto;
import com.marco.shop.model.customerAddress.CustomerAddressDto;
import com.marco.shop.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/address")
public class CustomerAddressController {
    @Autowired
    private CustomerAddressService addressService;

    @PostMapping
    public ResponseEntity<CustomerDto> addAddress(@RequestBody CustomerAddressDto addressDto) {
        CustomerDto customerUpdated = addressService.create(addressDto);

        return ResponseEntity.ok(customerUpdated);
    }

    @PatchMapping
    public ResponseEntity<CustomerDto> updateAddress(@RequestBody CustomerAddressDto addressDto) {
        CustomerDto customerUpdated = addressService.update(addressDto);

        return ResponseEntity.ok(customerUpdated);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> updateAddress(@PathVariable("id") Long addressId) {
        CustomerDto customerUpdated = addressService.delete(addressId);

        return ResponseEntity.ok(customerUpdated);
    }
}
