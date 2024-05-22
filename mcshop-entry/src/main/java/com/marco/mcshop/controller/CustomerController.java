package com.marco.mcshop.controller;

import com.marco.mcshop.model.dto.CustomerDto;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping(path = "/me")
    public ResponseEntity<CustomerDto> me() throws Exception {
        CustomerDto customerDto = customerMapper.toDto(customerService.getCurrentCustomer());

        return ResponseEntity.ok(customerDto);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> updateBasicInfo(
            @PathVariable("id") Long id,
            @RequestBody CustomerDto customerDto
    ) {
        CustomerDto updatedCustomer = customerService.updateBasicInfo(id, customerDto);

        return ResponseEntity.ok(updatedCustomer);
    }
}
