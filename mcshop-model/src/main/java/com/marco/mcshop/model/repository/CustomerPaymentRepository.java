package com.marco.mcshop.model.repository;

import com.marco.mcshop.model.entity.CustomerPayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPaymentRepository extends CrudRepository<CustomerPayment, Long> {
}
