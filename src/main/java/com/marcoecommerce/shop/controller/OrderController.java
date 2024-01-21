package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.mapper.impl.OrderMapper;
import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @PostMapping(path = "/orders")
    public ResponseEntity<OrderDto> createOrders(@RequestBody OrderDto orderDto) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDto);
        OrderEntity savedOrderEntity = orderService.create(orderEntity);

        return new ResponseEntity<>(orderMapper.toDto(savedOrderEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/orders")
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderEntity> orderEntities = orderService.findAll();
        return new ResponseEntity<>(orderEntities.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/orders/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id) {
        Optional<OrderEntity> foundOrder = orderService.findById(id);
        return foundOrder.map(orderEntity -> new ResponseEntity<>(orderMapper.toDto(orderEntity), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/orders/{id}")
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable("id") Long id,
            @RequestBody OrderDto orderDto
    ) {
        if(!orderService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OrderEntity orderEntity = orderMapper.toEntity(orderDto);
        OrderEntity updatedOrder = orderService.update(id, orderEntity);
        return new ResponseEntity<>(
                orderMapper.toDto(updatedOrder),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/orders/{id}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable("id") Long id) {
        if(!orderService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
