package com.marcoecommerce.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressEntity;
import com.marcoecommerce.shop.model.customerPayment.CustomerPaymentEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;

    // avoid sensitive info returning, like password

    @NotBlank
    private String email;

    private String phone;

    private String nickname;

    private String firstName;

    private String lastName;

    private List<CustomerAddressEntity> addressList = new ArrayList<>();

    private List<CustomerPaymentEntity> paymentList = new ArrayList<>();

    private List<OrderEntity> orderList = new ArrayList<>();

    private ShoppingCartEntity shoppingCart;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deletedAt;
}