package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.exception.address.AddressNotFoundException;
import com.marcoecommerce.shop.exception.customer.CustomerNotFoundException;
import com.marcoecommerce.shop.mapper.impl.CustomerAddressMapper;
import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressDto;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressEntity;
import com.marcoecommerce.shop.repository.CustomerRepository;
import com.marcoecommerce.shop.service.CustomerAddressService;
import com.marcoecommerce.shop.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;


@Service
@Slf4j
public class CustomerAddressServiceImpl implements CustomerAddressService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAddressMapper addressMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CustomerDto create(CustomerAddressDto addressDto) {
        CustomerAddressEntity newAddressEntity = addressMapper.toEntity(addressDto);
        CustomerEntity currentCustomer = customerService.getCurrentCustomer();

        currentCustomer.addAddress(newAddressEntity);
        CustomerEntity addressSaved = customerRepository.save(currentCustomer);

        return customerMapper.toDto(addressSaved);
    }

    @Override
    public CustomerDto update(CustomerAddressDto addressDto) {
        AtomicBoolean isAddressExist = new AtomicBoolean(false);
        CustomerEntity currentCustomer = customerService.getCurrentCustomer();

        currentCustomer.getAddressList().forEach(address -> {
            if (address.getId().equals(addressDto.getId())) {
                isAddressExist.set(true);
                address.setAddressLine1(addressDto.getAddressLine1());
                address.setAddressLine2(addressDto.getAddressLine2());
                address.setCity(addressDto.getCity());
                address.setPhone(addressDto.getPhone());
                address.setCountry(addressDto.getCountry());
                address.setPostalCode(addressDto.getPostalCode());
            }
        });

        if (!isAddressExist.get()) {
            throw new AddressNotFoundException(addressDto.getId());
        }

        CustomerEntity customerUpdated = customerRepository.save(currentCustomer);

        return customerMapper.toDto(customerUpdated);
    }

    @Override
    public CustomerDto delete(Long addressId) {
        CustomerEntity currentCustomer = customerService.getCurrentCustomer();

        CustomerAddressEntity existingAddress = currentCustomer.getAddressList().stream().filter(address -> address.getId().equals(addressId)).findFirst().orElseThrow(
                () -> {
                    log.error("Address not found with ID: " + addressId);
                    return new AddressNotFoundException(addressId);
                }
        );
        currentCustomer.removeAddress(existingAddress);

        CustomerEntity customerUpdated = customerRepository.save(currentCustomer);

        return customerMapper.toDto(customerUpdated);
    }
}
