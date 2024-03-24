package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.address.AddressNotFoundException;
import com.marco.mcshop.service.CustomerAddressService;
import com.marco.mcshop.service.CustomerService;
import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.dto.customerAddress.CustomerAddressDto;
import com.marco.mcshop.model.entity.CustomerAddressEntity;
import com.marco.mcshop.model.entity.CustomerEntity;
import com.marco.mcshop.model.mapper.impl.CustomerAddressMapper;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.model.repository.CustomerRepository;
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
