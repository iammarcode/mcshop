package com.marco.mcshop.model.repository;

import com.marco.mcshop.model.entity.ProductInventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends CrudRepository<ProductInventory, Long> {
}
