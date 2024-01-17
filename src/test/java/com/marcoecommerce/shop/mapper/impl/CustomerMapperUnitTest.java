package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.utils.TestDataUtil;
import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class CustomerMapperUnitTest {

    @Autowired
    private Mapper<CustomerEntity, CustomerDto> underTest;

    @Test
    public void givenCustomerEntity_whenMapToDto_thenSuccess() {
        // given
        CustomerEntity customerAEntity = TestDataUtil.createCustomerEntityA();
        OrderEntity orderA = TestDataUtil.createOrderEntityA();
        OrderEntity orderB = TestDataUtil.createOrderEntityB();
        customerAEntity.addOrder(orderA);
        customerAEntity.addOrder(orderB);

        // when
        CustomerDto customerDtoA = underTest.mapTo(customerAEntity);

        // then
        assertEquals(customerDtoA.getId(), customerAEntity.getId());
        assertEquals(customerDtoA.getEmail(), customerAEntity.getEmail());
        assertEquals(customerDtoA.getFirstName(), customerAEntity.getFirstName());
        assertEquals(customerDtoA.getLastName(), customerAEntity.getLastName());
        assertEquals(customerDtoA.getPassword(), customerAEntity.getPassword());
        assertEquals(customerDtoA.getPhone(), customerAEntity.getPhone());

        assertEquals(customerDtoA.getOrderList(), customerAEntity.getOrderList());
    }

    @Test
    public void givenCustomerDto_whenMapToEntity_thenSuccess() {
        // given
        CustomerDto customerDtoA = TestDataUtil.createCustomerDtoA();

        // when
        CustomerEntity customerAEntity = underTest.mapFrom(customerDtoA);

        // then
        assertEquals(customerAEntity.getId(), customerDtoA.getId());
        assertEquals(customerAEntity.getEmail(), customerDtoA.getEmail());
        assertEquals(customerAEntity.getFirstName(), customerDtoA.getFirstName());
        assertEquals(customerAEntity.getLastName(), customerDtoA.getLastName());
        assertEquals(customerAEntity.getPassword(), customerDtoA.getPassword());
        assertEquals(customerAEntity.getPhone(), customerDtoA.getPhone());

        assertEquals(customerAEntity.getOrderList(), customerDtoA.getOrderList());
    }

}
