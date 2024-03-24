package com.marco.mcshop.model.repository;

import com.marco.mcshop.model.entity.ShoppingCartEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCartEntity, Long> {
}
