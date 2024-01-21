package com.marcoecommerce.shop.model.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
