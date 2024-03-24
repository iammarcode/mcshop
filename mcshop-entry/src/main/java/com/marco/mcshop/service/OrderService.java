package com.marco.mcshop.service;

import com.marco.mcshop.model.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderEntity create(OrderEntity customerEntity);

    List<OrderEntity> findAll();

    Optional<OrderEntity> findById(Long id);

    boolean isExist(Long id);

    void deleteById(Long id);

    OrderEntity update(Long id, OrderEntity authorEntity);
}
