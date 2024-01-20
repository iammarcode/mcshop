package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.customer.CustomerResponse;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping(path = "")
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        List<CustomerEntity> customerEntities = customerService.findAll();

        return new ResponseEntity<>(customerEntities.stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") Long id) {
        Optional<CustomerEntity> foundCustomer = customerService.findById(id);

        return foundCustomer.map(customerEntity -> new ResponseEntity<>(customerMapper.toResponse(customerEntity), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable("id") Long id,
            @RequestBody CustomerResponse customerResponse
    ) {
        if(!customerService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerEntity customerEntity = customerMapper.toEntity(customerResponse);
        CustomerEntity updatedCustomer = customerService.update(id, customerEntity);
        return new ResponseEntity<>(
                customerMapper.toResponse(updatedCustomer),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable("id") Long id) {
        if(!customerService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
