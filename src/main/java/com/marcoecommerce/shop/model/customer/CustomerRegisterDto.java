package com.marcoecommerce.shop.model.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterDto {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String otp;

    private String phone;

    private String nickname;

    private String firstName;

    private String lastName;
}
