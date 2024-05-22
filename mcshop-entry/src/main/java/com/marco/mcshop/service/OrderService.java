package com.marco.mcshop.service;

import com.marco.mcshop.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order create(Order customerEntity);

    List<Order> findAll();

    Optional<Order> findById(Long id);

    boolean isExist(Long id);

    void deleteById(Long id);

    Order update(Long id, Order authorEntity);
}
