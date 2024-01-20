package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.repository.CustomerRepository;
import com.marcoecommerce.shop.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationImpl implements AuthenticationService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomerEntity register(CustomerEntity customer) {
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

        return customerRepository.findByEmail(customer.getEmail()).orElseThrow();
    }
}
