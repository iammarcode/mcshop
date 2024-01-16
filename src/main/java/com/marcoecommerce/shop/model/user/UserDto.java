package com.marcoecommerce.shop.model.user;

import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import com.marcoecommerce.shop.model.userAddress.UserAddressEntity;
import com.marcoecommerce.shop.model.userPayment.UserPaymentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "shoppingCart")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String email;

    private String phone;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private List<UserAddressEntity> addressList = new ArrayList<>();

    private List<UserPaymentEntity> paymentList = new ArrayList<>();

    private List<OrderEntity> orderList = new ArrayList<>();

    private ShoppingCartEntity shoppingCart;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}