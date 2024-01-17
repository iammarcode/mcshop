package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.utils.TestDataUtil;
import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.model.order.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderMapperUnitTest {

    @Autowired
    private Mapper<OrderEntity, OrderDto> underTest;

    @Test
    public void givenOrderEntity_whenMapTo_thenReturnOrderDto() {
        // given
        OrderEntity orderAEntity = TestDataUtil.createOrderEntityA();
        orderAEntity.setTransaction(TestDataUtil.createOrderTransactionEntityA());
        orderAEntity.setCustomer(TestDataUtil.createCustomerEntityA());
        orderAEntity.setAddress(TestDataUtil.createCustomerAddressEntityA());
        orderAEntity.setOrderItemList(List.of(TestDataUtil.createOrderItemEntityA(), TestDataUtil.createOrderItemEntityB()));

        // when
        OrderDto orderDtoA = underTest.mapTo(orderAEntity);

        // then
        assertEquals(orderDtoA.getId(), orderAEntity.getId());
        assertEquals(orderDtoA.getStatus(), orderAEntity.getStatus());
        assertEquals(orderDtoA.getTotal(), orderAEntity.getTotal());
        assertEquals(orderDtoA.getOrderItemList(), orderAEntity.getOrderItemList());
        assertEquals(orderDtoA.getAddress(), orderAEntity.getAddress());
        assertEquals(orderDtoA.getTransaction(), orderAEntity.getTransaction());
        assertEquals(orderDtoA.getCustomer(), orderAEntity.getCustomer());
    }

    @Test
    public void givenOrderDto_whenMapFrom_thenReturnOrderEntity() {
        // given
        OrderDto orderADto = TestDataUtil.createOrderDtoA();
        orderADto.setTransaction(TestDataUtil.createOrderTransactionEntityA());
        orderADto.setCustomer(TestDataUtil.createCustomerEntityA());
        orderADto.setAddress(TestDataUtil.createCustomerAddressEntityA());
        orderADto.setOrderItemList(List.of(TestDataUtil.createOrderItemEntityA(), TestDataUtil.createOrderItemEntityB()));

        // when
        OrderEntity orderAEntity = underTest.mapFrom(orderADto);

        // then
        assertEquals(orderAEntity.getId(), orderADto.getId());
        assertEquals(orderAEntity.getStatus(), orderADto.getStatus());
        assertEquals(orderAEntity.getTotal(), orderADto.getTotal());
        assertEquals(orderAEntity.getOrderItemList(), orderADto.getOrderItemList());
        assertEquals(orderAEntity.getAddress(), orderADto.getAddress());
        assertEquals(orderAEntity.getTransaction(), orderADto.getTransaction());
        assertEquals(orderAEntity.getCustomer(), orderADto.getCustomer());
    }

}
