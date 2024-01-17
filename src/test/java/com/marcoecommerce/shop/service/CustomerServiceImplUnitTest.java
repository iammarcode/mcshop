package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.repository.CustomerRepository;
import com.marcoecommerce.shop.service.impl.CustomerServiceImpl;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerEntity customerA;
    private CustomerEntity customerB;

    @BeforeEach
    void setUp() {
        customerA = TestDataUtil.createCustomerEntityA();
        customerA.setId(1L);
        customerB = TestDataUtil.createCustomerEntityB();
        customerB.setId(2L);
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

        // when
        Optional<CustomerEntity> customerFound = customerService.findById(customerA.getId());

        // then
        verify(customerRepository, times(1)).findById(any(Long.class));
        assertTrue(customerFound.isPresent());
        assertEquals(customerA, customerFound.get());
    }

    @Test
    public void givenCustomer_whenFindById_thenReturnCustomer() {
        // given
        when(customerRepository.findAll()).thenReturn(List.of(customerA, customerB));

        // when
        List<CustomerEntity> result = customerService.findAll();

        // then
        verify(customerRepository, times(1)).findAll();
        assertTrue(result.containsAll(List.of(customerA, customerB)));
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

        // when
        customerA.setUsername("updated username");
        CustomerEntity customerUpdated = customerService.update(customerA.getId(), customerA);

        // then
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
        verify(customerRepository, times(1)).findById(any(Long.class));
        assertEquals("updated username", customerUpdated.getUsername());
    }

}
