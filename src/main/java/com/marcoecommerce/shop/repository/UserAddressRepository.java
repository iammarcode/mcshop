package com.marcoecommerce.shop.repository;

import com.marcoecommerce.shop.model.userAddress.UserAddressEntity;
import com.marcoecommerce.shop.model.userPayment.UserPaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends CrudRepository<UserAddressEntity, Long> {
}
