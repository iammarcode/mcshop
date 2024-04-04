package com.marco.mcshop.model.repository;

import com.marco.mcshop.model.entity.ShoppingCartItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItemEntity, Long> {
}
