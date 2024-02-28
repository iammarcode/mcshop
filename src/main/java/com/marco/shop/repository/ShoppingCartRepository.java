package com.marco.shop.repository;

import com.marco.shop.model.shoppingCart.ShoppingCartEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCartEntity, Long> {
}
