package com.marcoecommerce.shop.repository;

import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.userPayment.UserPaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepository extends CrudRepository<UserPaymentEntity, Long> {
}
