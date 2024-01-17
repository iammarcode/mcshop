package com.marcoecommerce.shop.repository;

import com.marcoecommerce.shop.model.customerPayment.CustomerPaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPaymentRepository extends CrudRepository<CustomerPaymentEntity, Long> {
}
