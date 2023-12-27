package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.utils.TestDataUtil;
import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.model.order.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderMapperUnitTest {
    private final Mapper<OrderEntity, OrderDto> orderMapper;

    @Autowired
    public OrderMapperUnitTest(Mapper<OrderEntity, OrderDto> orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Test
    public void TestConvertOrderEntityToDto() {
        OrderEntity orderAEntity = TestDataUtil.createOrderEntityA();
        OrderDto orderDtoA = orderMapper.mapTo(orderAEntity);

        assertEquals(orderDtoA.getId(), orderAEntity.getId());
        assertEquals(orderDtoA.getStatus(), orderAEntity.getStatus());
    }

    @Test
    public void TestConvertOrderDtoToEntity() {
        OrderDto orderDtoA = TestDataUtil.createOrderDtoA();
        OrderEntity orderEntityA = orderMapper.mapFrom(orderDtoA);

        assertEquals(orderEntityA.getId(), orderDtoA.getId());
        assertEquals(orderEntityA.getStatus(), orderDtoA.getStatus());
    }

}
