package com.marco.mcshop.model.repository;

import com.marco.mcshop.model.entity.CustomerAddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends CrudRepository<CustomerAddressEntity, Long> {
}
