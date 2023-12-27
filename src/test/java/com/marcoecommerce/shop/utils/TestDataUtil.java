package com.marcoecommerce.shop.utils;

import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.order.OrderStatus;

import java.util.ArrayList;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static CustomerEntity createCustomerEntityA() {
        return CustomerEntity.builder()
                .name("customerEntityA")
                .build();
    }

    public static CustomerEntity createCustomerEntityB() {
        return CustomerEntity.builder()
                .name("customerEntityB")
                .build();
    }

    public static CustomerEntity createCustomerEntityC() {
        return CustomerEntity.builder()
                .name("customerEntityC")
                .build();
    }

    public static CustomerDto createCustomerDtoA() {
        return CustomerDto.builder()
                .orders(new ArrayList<>())
                .name("customerDtoA")
                .build();
    }

    public static CustomerDto createCustomerDtoB() {
        return CustomerDto.builder()
                .orders(new ArrayList<>())
                .name("customerDtoB")
                .build();
    }

    public static OrderEntity createOrderEntityA() {
        return OrderEntity.builder()
                .status(OrderStatus.ORDERED)
                .customer(null)
                .build();
    }

    public static OrderEntity createOrderEntityB() {
        return OrderEntity.builder()
                .status(OrderStatus.ORDERED)
                .customer(null)
                .build();
    }

    public static OrderEntity createOrderEntityC() {
        return OrderEntity.builder()
                .status(OrderStatus.ORDERED)
                .customer(null)
                .build();
    }

    public static OrderDto createOrderDtoA() {
        return OrderDto.builder()
                .status(OrderStatus.ORDERED)
                .customer(null)
                .build();
    }

    public static OrderDto createOrderDtoB() {
        return OrderDto.builder()
                .status(OrderStatus.ORDERED)
                .customer(null)
                .build();
    }
}
