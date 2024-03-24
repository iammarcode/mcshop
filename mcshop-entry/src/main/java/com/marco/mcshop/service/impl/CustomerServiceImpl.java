package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.customer.CustomerNotFoundException;
import com.marco.mcshop.service.CustomerService;
import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.entity.CustomerEntity;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.model.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    @Override
    public List<CustomerDto> findAll() {
        List<CustomerEntity> customerEntityList = StreamSupport.stream(customerRepository.findAll().spliterator(), false).toList();

        return customerEntityList.stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    public CustomerDto findById(Long id) {
        Optional<CustomerEntity> customerFound = customerRepository.findById(id);

        if (customerFound.isEmpty()) {
            log.error("Customer not found with ID: " + id);
            throw new CustomerNotFoundException(id);
        }

        return customerMapper.toDto(customerFound.get());
    }

    @Override
    public boolean isExist(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id)) {
            log.error("Customer not found with ID: " + id);
            throw new CustomerNotFoundException(id);
        }

        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto updateInfo(Long id, CustomerDto customerDto) {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setNickname(customerDto.getNickname());
            existingCustomer.setFirstName(customerDto.getFirstName());
            existingCustomer.setLastName(customerDto.getLastName());

//            for (OrderEntity order : customer.getOrderList()) {
//                existingCustomer.addOrder(order);
//            }
//            for (CustomerPaymentEntity payment : customer.getPaymentList()) {
//                existingCustomer.addPayment(payment);
//            }

            return customerMapper.toDto(customerRepository.save(existingCustomer));
        }).orElseThrow(() ->
            {
                log.error("Customer not found with ID: " + id);
                return new CustomerNotFoundException(id);
            }
        );
    }

    @Override
    public CustomerEntity findByEmail(String email) {
        Optional<CustomerEntity> customerFound = customerRepository.findByEmail(email);

        if (customerFound.isEmpty()) {
            log.error("Customer not found with email: " + email);
            throw new CustomerNotFoundException(email);
        }

        return customerFound.get();
    }

    @Override
    public CustomerEntity getCurrentCustomer() {
        // TODO: customer update?
        CustomerEntity currentCustomer = (CustomerEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return customerRepository.findByEmail(currentCustomer.getEmail()).orElseThrow(
                () -> {
                    log.error("Customer not found with email: " + currentCustomer.getEmail());
                    return new CustomerNotFoundException(currentCustomer.getEmail());
                }
        );
    }

    @Override
    public UserDetails findUserDetailsByEmail(String email) {
        CustomerEntity customerFound = customerRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.error("Customer not found with email: " + email);
                    return new CustomerNotFoundException(email);
                }
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
