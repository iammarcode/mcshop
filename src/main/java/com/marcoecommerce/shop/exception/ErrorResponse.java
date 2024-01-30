package com.marcoecommerce.shop.exception;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorResponse {
    @NotBlank
    private int statusCode;

    @NotBlank
    private String message;
}
