package com.marco.mcshop.model.repository;

import com.marco.mcshop.model.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);
    Optional<CustomerEntity> findByEmail(String email);
}
