package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import com.marcoecommerce.shop.model.user.UserEntity;
import com.marcoecommerce.shop.model.userAddress.UserAddressEntity;
import com.marcoecommerce.shop.model.userPayment.UserPaymentEntity;
import com.marcoecommerce.shop.repository.UserRepository;
import com.marcoecommerce.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity create(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity update(Long id, UserEntity user) {
        user.setId(id);

        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(user.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(user.getPhone()).ifPresent(existingUser::setPhone);
            Optional.ofNullable(user.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(user.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(user.getLastName()).ifPresent(existingUser::setLastName);

            for (OrderEntity order : user.getOrderList()) {
                existingUser.addOrder(order);
            }

            for (UserPaymentEntity payment : user.getPaymentList()) {
                existingUser.addPayment(payment);
            }

            for (UserAddressEntity address : user.getAddressList()) {
                existingUser.addAddress(address);
            }

            if (user.getShoppingCart() != null) {
                existingUser.addShoppingCart(user.getShoppingCart());
            }

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }
}
