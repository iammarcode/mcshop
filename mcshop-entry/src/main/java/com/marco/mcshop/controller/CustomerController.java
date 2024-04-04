package com.marco.mcshop.controller;

import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping(path = "/current")
    public ResponseEntity<CustomerDto> getCurrentCustomer() {
        CustomerDto customerDto = customerMapper.toDto(customerService.getCurrentCustomer());

        return ResponseEntity.ok(customerDto);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> customerDtoList = customerService.findAll();

        return ResponseEntity.ok(customerDtoList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id) {
        CustomerDto customerFoundDto = customerService.findById(id);

        return ResponseEntity.ok(customerFoundDto);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable("id") Long id,
            @RequestBody CustomerDto customerDto
    ) {
        CustomerDto updatedCustomer = customerService.updateInfo(id, customerDto);

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
