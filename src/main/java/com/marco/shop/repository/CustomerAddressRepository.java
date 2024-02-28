package com.marco.shop.repository;

import com.marco.shop.model.customerAddress.CustomerAddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends CrudRepository<CustomerAddressEntity, Long> {
}
