package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.customer.CustomerDto;
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
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerEntity> customerEntities = customerService.findAll();

        return new ResponseEntity<>(customerEntities.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id) {
        Optional<CustomerEntity> foundCustomer = customerService.findById(id);

        return foundCustomer.map(customerEntity -> new ResponseEntity<>(customerMapper.toDto(customerEntity), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable("id") Long id,
            @RequestBody CustomerDto customerDto
    ) {
        if(!customerService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
        CustomerEntity updatedCustomer = customerService.update(id, customerEntity);
        return new ResponseEntity<>(
                customerMapper.toDto(updatedCustomer),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable("id") Long id) {
        if(!customerService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
