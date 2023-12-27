package com.marcoecommerce.shop.repository;

import com.marcoecommerce.shop.model.order.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
