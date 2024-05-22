package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.address.AddressNotFoundException;
import com.marco.mcshop.model.dto.CustomerDto;
import com.marco.mcshop.model.dto.CustomerAddressDto;
import com.marco.mcshop.model.entity.CustomerAddress;
import com.marco.mcshop.model.entity.Customer;
import com.marco.mcshop.model.mapper.impl.CustomerAddressMapper;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.model.repository.CustomerRepository;
import com.marco.mcshop.service.CustomerAddressService;
import com.marco.mcshop.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final CustomerAddressMapper addressMapper;
    private final CustomerMapper customerMapper;

    public CustomerAddressServiceImpl(CustomerRepository customerRepository, CustomerService customerService, CustomerAddressMapper addressMapper, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.addressMapper = addressMapper;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerAddressDto> create(CustomerAddressDto addressDto) throws Exception {
        CustomerAddress newAddressEntity = addressMapper.toEntity(addressDto);
        Customer currentCustomer = customerService.getCurrentCustomer();

        currentCustomer.addAddress(newAddressEntity);
        Customer customerUpdated = customerRepository.save(currentCustomer);

        CustomerDto customerDtoUpdated = customerMapper.toDto(customerUpdated);

        return customerDtoUpdated.getAddressList();
    }

    @Override
    public List<CustomerAddressDto> partialUpdate(Long id, CustomerAddressDto addressDto) throws Exception {
        AtomicBoolean isAddressExist = new AtomicBoolean(false);
        Customer currentCustomer = customerService.getCurrentCustomer();

        currentCustomer.getAddressList().forEach(address -> {
            if (address.getId().equals(id)) {
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

        Customer customerUpdated = customerRepository.save(currentCustomer);

        CustomerDto customerUpdatedDto = customerMapper.toDto(customerUpdated);

        return customerUpdatedDto.getAddressList();
    }

    @Override
    public List<CustomerAddressDto> delete(Long addressId) throws Exception {
        Customer currentCustomer = customerService.getCurrentCustomer();

        CustomerAddress existingAddress = currentCustomer.getAddressList().stream().filter(address -> address.getId().equals(addressId)).findFirst().orElseThrow(
                () -> new AddressNotFoundException(addressId)
        );
        currentCustomer.removeAddress(existingAddress);

        Customer customerUpdated = customerRepository.save(currentCustomer);

        CustomerDto customerUpdatedDto = customerMapper.toDto(customerUpdated);

        return customerUpdatedDto.getAddressList();
    }
}
