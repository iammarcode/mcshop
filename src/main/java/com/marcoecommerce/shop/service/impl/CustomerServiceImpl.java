package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.exception.customer.CustomerNotFoundException;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressEntity;
import com.marcoecommerce.shop.model.customerPayment.CustomerPaymentEntity;
import com.marcoecommerce.shop.repository.CustomerRepository;
import com.marcoecommerce.shop.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    @Override
    public List<CustomerEntity> findAll() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerEntity findById(Long id) {
        Optional<CustomerEntity> customerFound = customerRepository.findById(id);

        if (customerFound.isEmpty()) {
            log.error("Customer not found with ID: " + id);
            throw new CustomerNotFoundException(id);
        }

        return customerFound.get();
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
    public CustomerEntity update(Long id, CustomerEntity customer) {
        customer.setId(id);

        return customerRepository.findById(id).map(existingCustomer -> {
            Optional.ofNullable(customer.getNickname()).ifPresent(existingCustomer::setNickname);
            Optional.ofNullable(customer.getEmail()).ifPresent(existingCustomer::setEmail);
            Optional.ofNullable(customer.getPhone()).ifPresent(existingCustomer::setPhone);
            Optional.ofNullable(customer.getPassword()).ifPresent(existingCustomer::setPassword);
            Optional.ofNullable(customer.getFirstName()).ifPresent(existingCustomer::setFirstName);
            Optional.ofNullable(customer.getLastName()).ifPresent(existingCustomer::setLastName);

            for (OrderEntity order : customer.getOrderList()) {
                existingCustomer.addOrder(order);
            }

            for (CustomerPaymentEntity payment : customer.getPaymentList()) {
                existingCustomer.addPayment(payment);
            }

            for (CustomerAddressEntity address : customer.getAddressList()) {
                existingCustomer.addAddress(address);
            }

            if (customer.getShoppingCart() != null) {
                existingCustomer.addShoppingCart(customer.getShoppingCart());
            }

            return customerRepository.save(existingCustomer);
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
}
