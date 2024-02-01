package com.marcoecommerce.shop.exception;

import com.marcoecommerce.shop.exception.auth.OtpValidationFailedException;
import com.marcoecommerce.shop.exception.auth.RefreshTokenInvalidException;
import com.marcoecommerce.shop.exception.customer.CustomerAlreadyExistException;
import com.marcoecommerce.shop.exception.customer.CustomerNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler({CustomerAlreadyExistException.class})
    public ResponseEntity<String> handleCustomerAlreadyExistsException(CustomerAlreadyExistException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({OtpValidationFailedException.class})
    public ResponseEntity<Object> handleOtpValidationFailedException(OtpValidationFailedException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<Object> handleAuthenticationException(JwtException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RefreshTokenInvalidException.class})
    public ResponseEntity<Object> handleRefreshTokenInvalidException(RefreshTokenInvalidException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    // unexpected exception
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleUnexpectedException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}

