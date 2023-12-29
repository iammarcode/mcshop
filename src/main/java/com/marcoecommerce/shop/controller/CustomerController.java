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
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }


    @PostMapping(path = "/customers")
    public ResponseEntity<CustomerDto> createCustomers(@RequestBody CustomerDto customerDto) {
        CustomerEntity customerEntity = customerMapper.mapFrom(customerDto);
        CustomerEntity savedCustomerEntity = customerService.save(customerEntity);

        return new ResponseEntity<>(customerMapper.mapTo(savedCustomerEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/customers")
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerEntity> customerEntities = customerService.findAll();
        return new ResponseEntity<>(customerEntities.stream()
                .map(customerMapper::mapTo)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/customersj")
    public ResponseEntity<String> getCustomersj() {
        List<CustomerEntity> customerEntities = customerService.findAll();
        return new ResponseEntity<>("fkjlajalfjlsjsjsl", HttpStatus.CREATED);
    }

    @GetMapping(path = "/customers/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id) {
        Optional<CustomerEntity> foundCustomer = customerService.findById(id);
        return foundCustomer.map(customerEntity -> new ResponseEntity<>(customerMapper.mapTo(customerEntity), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/customers/{id}")
    public ResponseEntity<CustomerDto> fullUpdateCustomer(
            @PathVariable("id") Long id,
            @RequestBody CustomerDto customerDto) {

        if(!customerService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerDto.setId(id);
        CustomerEntity customerEntity = customerMapper.mapFrom(customerDto);
        CustomerEntity savedCustomerEntity = customerService.save(customerEntity);
        return new ResponseEntity<>(
                customerMapper.mapTo(savedCustomerEntity),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/customers/{id}")
    public ResponseEntity<CustomerDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody CustomerDto customerDto
    ) {
        if(!customerService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerEntity customerEntity = customerMapper.mapFrom(customerDto);
        CustomerEntity updatedCustomer = customerService.partialUpdate(id, customerEntity);
        return new ResponseEntity<>(
                customerMapper.mapTo(updatedCustomer),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/customers/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
