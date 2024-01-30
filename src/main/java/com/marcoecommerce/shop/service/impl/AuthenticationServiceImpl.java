package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.exception.customer.CustomerAlreadyExistException;
import com.marcoecommerce.shop.exception.customer.CustomerNotFoundException;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.repository.CustomerRepository;
import com.marcoecommerce.shop.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomerEntity register(CustomerEntity customer) {
        Optional<CustomerEntity> customerFound = customerRepository.findByEmail(customer.getEmail());
        if (customerFound.isPresent()) {
            throw new CustomerAlreadyExistException(customer.getEmail());
        }

        // hash password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return customerRepository.save(customer);
    }

    @Override
    public CustomerEntity login(CustomerEntity customer) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customer.getEmail(),
                        customer.getPassword()
                )
        );

        Optional<CustomerEntity> customerFound = customerRepository.findByEmail(customer.getEmail());
        if (customerFound.isEmpty()) {
            throw new CustomerNotFoundException(customer.getEmail());
        }

        return customerFound.get();
    }

    // TODO: refresh token
}
