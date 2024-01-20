package com.marcoecommerce.shop.model.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String phone;

    private String nickname;

    private String firstName;

    private String lastName;
}
