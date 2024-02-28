package com.marco.shop.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marco.shop.model.customerPayment.CustomerPaymentDto;
import com.marco.shop.model.shoppingCart.ShoppingCartDto;
import com.marco.shop.model.customerAddress.CustomerAddressDto;
import com.marco.shop.model.order.OrderDto;
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

    private List<CustomerAddressDto> addressList = new ArrayList<>();

    private List<CustomerPaymentDto> paymentList = new ArrayList<>();

    private List<OrderDto> orderList = new ArrayList<>();

    private ShoppingCartDto shoppingCart;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deletedAt;
}