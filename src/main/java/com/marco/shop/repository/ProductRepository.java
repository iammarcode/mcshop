package com.marco.shop.repository;

import com.marco.shop.model.product.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
}
