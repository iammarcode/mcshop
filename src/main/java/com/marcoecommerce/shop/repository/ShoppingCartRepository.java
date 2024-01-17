package com.marcoecommerce.shop.repository;

import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCartEntity, Long> {
}
