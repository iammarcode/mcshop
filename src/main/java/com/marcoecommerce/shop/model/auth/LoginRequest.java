package com.marcoecommerce.shop.model.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
