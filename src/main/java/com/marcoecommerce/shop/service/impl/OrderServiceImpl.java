package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.repository.OrderRepository;
import com.marcoecommerce.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Override
    public List<OrderEntity> findAll() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderEntity partialUpdate(Long id, OrderEntity orderEntity) {
        orderEntity.setId(id);

        return orderRepository.findById(id).map(existingOrder -> {
            Optional.ofNullable(orderEntity.getStatus()).ifPresent(existingOrder::setStatus);
            // TODO: update customer?
            return orderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Order does not exist"));
    }
}
