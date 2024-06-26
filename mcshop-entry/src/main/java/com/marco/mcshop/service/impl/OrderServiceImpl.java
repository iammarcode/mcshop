package com.marco.mcshop.service.impl;

import com.marco.mcshop.model.entity.OrderEntity;
import com.marco.mcshop.model.repository.OrderRepository;
import com.marco.mcshop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity create(OrderEntity orderEntity) {
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
    public OrderEntity update(Long id, OrderEntity order) {
        order.setId(id);

        return orderRepository.findById(id).map(existingOrder -> {
            Optional.ofNullable(order.getStatus()).ifPresent(existingOrder::setStatus);
            Optional.ofNullable(order.getTotal()).ifPresent(existingOrder::setTotal);

            if (order.getTransaction() != null) {
                existingOrder.addTransaction(order.getTransaction());
            }

            return orderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Order does not exist"));
    }
}
