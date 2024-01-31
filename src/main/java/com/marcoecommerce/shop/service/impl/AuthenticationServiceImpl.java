package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.exception.auth.OtpValidationFailedException;
import com.marcoecommerce.shop.exception.customer.CustomerAlreadyExistException;
import com.marcoecommerce.shop.exception.customer.CustomerNotFoundException;
import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerLoginDto;
import com.marcoecommerce.shop.model.customer.CustomerRegisterDto;
import com.marcoecommerce.shop.repository.CustomerRepository;
import com.marcoecommerce.shop.service.AuthenticationService;
import com.marcoecommerce.shop.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String getOtp(String email) {
        // check email
        boolean existsByEmail = customerRepository.existsByEmail(email);
        if (existsByEmail) {
            throw new CustomerAlreadyExistException(email);
        }

        return otpService.generateOTP(email);
    }

    @Override
    public CustomerEntity register(CustomerRegisterDto customer) {
        // check otp
        String otpCached = otpService.getOtpByKey(customer.getEmail());
        if (!customer.getOtp().equals(otpCached)) {
            throw new OtpValidationFailedException(customer.getOtp());
        }

        // hash password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        // create customer
        CustomerEntity customerRegister = customerMapper.toEntityRegister(customer);
        CustomerEntity customerSaved = customerRepository.save(customerRegister);

        // clear otp cache
        otpService.clearOtpByKey(customer.getEmail());

        return customerSaved;
    }

    @Override
    public CustomerEntity login(CustomerLoginDto customer) {
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
