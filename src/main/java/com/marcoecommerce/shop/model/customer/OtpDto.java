package com.marcoecommerce.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressEntity;
import com.marcoecommerce.shop.model.customerPayment.CustomerPaymentEntity;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpDto {
    @NotBlank
    private String email;
}