package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.repository.CustomerRepository;
import com.marcoecommerce.shop.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity save(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    @Override
    public List<CustomerEntity> findAll() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerEntity> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerEntity partialUpdate(Long id, CustomerEntity customerEntity) {
        customerEntity.setId(id);

        return customerRepository.findById(id).map(existingCustomer -> {
            Optional.ofNullable(customerEntity.getName()).ifPresent(existingCustomer::setName);
            // TODO: update orders?
            return customerRepository.save(existingCustomer);
        }).orElseThrow(() -> new RuntimeException("Customer does not exist"));
    }
}
