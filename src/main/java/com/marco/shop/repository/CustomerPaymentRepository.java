package com.marco.shop.repository;

import com.marco.shop.model.customerPayment.CustomerPaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPaymentRepository extends CrudRepository<CustomerPaymentEntity, Long> {
}
