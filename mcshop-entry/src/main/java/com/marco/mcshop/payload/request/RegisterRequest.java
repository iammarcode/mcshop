package com.marco.mcshop.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
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
