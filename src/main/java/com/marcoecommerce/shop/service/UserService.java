package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity create(UserEntity userEntity);

    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    boolean isExist(Long id);

    void deleteById(Long id);

    UserEntity update(Long id, UserEntity authorEntity);
}
