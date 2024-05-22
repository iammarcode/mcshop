package com.marco.mcshop.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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