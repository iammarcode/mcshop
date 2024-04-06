package com.marco.mcshop.controller;

import com.marco.mcshop.model.dto.customerAddress.CustomerAddressDto;
import com.marco.mcshop.service.CustomerAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final CustomerAddressService addressService;

    public AddressController(CustomerAddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<List<CustomerAddressDto>> add(@RequestBody CustomerAddressDto addressDto) throws Exception {
        List<CustomerAddressDto> addressListUpdated = addressService.create(addressDto);

        return ResponseEntity.ok(addressListUpdated);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<List<CustomerAddressDto>> updateAddress(
            @PathVariable Long id,
            @RequestBody CustomerAddressDto addressDto
    ) throws Exception {
        List<CustomerAddressDto> addressListUpdated = addressService.partialUpdate(id, addressDto);

        return ResponseEntity.ok(addressListUpdated);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<List<CustomerAddressDto>> updateAddress(@PathVariable("id") Long addressId) throws Exception {
        List<CustomerAddressDto> addressListUpdated = addressService.delete(addressId);

        return ResponseEntity.ok(addressListUpdated);
    }
}
