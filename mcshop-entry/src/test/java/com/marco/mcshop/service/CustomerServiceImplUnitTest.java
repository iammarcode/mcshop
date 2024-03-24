package com.marco.mcshop.service;

import com.marco.mcshop.service.impl.CustomerServiceImpl;
import com.marco.mcshop.utils.TestDataUtil;
import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.entity.CustomerEntity;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.model.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerEntity customerA;
    private CustomerEntity customerB;
    private CustomerDto customerADto;
    private CustomerDto customerBDto;

    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerA = TestDataUtil.createCustomerEntityA();
        customerA.setId(1L);
        customerB = TestDataUtil.createCustomerEntityB();
        customerB.setId(2L);

        customerADto = TestDataUtil.createCustomerDtoA();
        customerADto.setId(1L);
        customerBDto = TestDataUtil.createCustomerDtoB();
        customerBDto.setId(2L);
    }

    @Test
    public void givenCustomer_whenSaveCustomer_thenReturnCustomer() {
        // given
        when(customerRepository.save(customerA)).thenReturn(customerA);

        // when
        CustomerEntity customerSaved = customerService.create(customerA);

        // then
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
        assertEquals(customerA, customerSaved);
    }

    @Test
    public void givenCustomer_whenFindAllCustomer_thenReturnAllCustomers() {
        // given
        when(customerRepository.findById(customerA.getId())).thenReturn(Optional.of(customerA));
        when(customerMapper.toDto(customerA)).thenReturn(customerADto);

        // when
        CustomerDto customerFound = customerService.findById(customerA.getId());

        // then
        verify(customerRepository, times(1)).findById(any(Long.class));
        assertEquals(customerADto, customerFound);
    }

    @Test
    public void givenCustomer_whenFindById_thenReturnCustomer() {
        // given
        when(customerRepository.findAll()).thenReturn(List.of(customerA, customerB));
        when(customerMapper.toDto(customerA)).thenReturn(customerADto);
        when(customerMapper.toDto(customerB)).thenReturn(customerBDto);

        // when
        List<CustomerDto> result = customerService.findAll();

        // then
        verify(customerRepository, times(1)).findAll();
        assertTrue(result.containsAll(List.of(customerADto, customerBDto)));
    }

    @Test
    public void givenCustomer_whenIsExist_thenReturnTrue() {
        // given
        when(customerRepository.existsById(customerA.getId())).thenReturn(true);

        // when
        boolean result = customerService.isExist(customerA.getId());

        // then
        verify(customerRepository, times(1)).existsById(any(Long.class));
        assertTrue(result);
    }

    @Test
    public void givenCustomer_whenDeleteById_thenSuccessful() {
        // given
        when(customerRepository.existsById(customerA.getId())).thenReturn(true);

        // when
        customerService.deleteById(customerA.getId());

        // then
        verify(customerRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void givenCustomer_whenUpdateCustomer_thenReturnUpdatedCustomer() {
        // given
        when(customerRepository.findById(customerA.getId())).thenReturn(Optional.of(customerA));
        when(customerRepository.save(customerA)).thenReturn(customerA);
        when(customerMapper.toDto(customerA)).thenReturn(customerADto);

        // when
        customerADto.setNickname("updated nickname");
        CustomerDto customerUpdated = customerService.updateInfo(customerA.getId(), customerADto);

        // then
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
        verify(customerRepository, times(1)).findById(any(Long.class));
        assertEquals("updated nickname", customerUpdated.getNickname());
    }

}
