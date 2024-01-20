package com.marcoecommerce.shop.model.customer;

import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressEntity;
import com.marcoecommerce.shop.model.customerPayment.CustomerPaymentEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "shoppingCart")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private Long id;

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

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}