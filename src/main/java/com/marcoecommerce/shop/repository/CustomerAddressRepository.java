package com.marcoecommerce.shop.repository;

import com.marcoecommerce.shop.model.customerAddress.CustomerAddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends CrudRepository<CustomerAddressEntity, Long> {
}
