package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.customer.CustomerNotFoundException;
import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.entity.CustomerEntity;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.model.repository.CustomerRepository;
import com.marco.mcshop.service.CustomerService;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    @Override
    public boolean isExist(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public CustomerDto updateBasicInfo(Long id, CustomerDto customerDto) {
        return customerRepository.findById(id).map(existingCustomer -> {
            if (customerDto.getNickname() != null) existingCustomer.setNickname(customerDto.getNickname());
            if (customerDto.getFirstName() != null) existingCustomer.setFirstName(customerDto.getFirstName());
            if (customerDto.getLastName() != null) existingCustomer.setLastName(customerDto.getLastName());

            CustomerEntity customerUpdated = customerRepository.save(existingCustomer);

            return customerMapper.toDto(customerUpdated);
        }).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public CustomerEntity getCurrentCustomer() throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            UserDetails currentCustomer = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return customerRepository.findByEmail(currentCustomer.getUsername()).orElseThrow(
                    () -> new CustomerNotFoundException(currentCustomer.getUsername())
            );
        }

        throw new JwtException("Authentication failed");
    }

    @Override
    public UserDetails findUserDetailsByEmail(String email) {
        CustomerEntity customerFound = customerRepository.findByEmail(email).orElseThrow(
                () -> new CustomerNotFoundException(email)
        );

        return User.builder()
                .username(customerFound.getEmail())
                .password(customerFound.getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .authorities(List.of())
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }


    // TODO: update email
    // TODO: update phone
    // TODO: update password
}
