package com.marco.mcshop.model.repository;

import com.marco.mcshop.model.entity.ShoppingCartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Long> {
}
