package com.marco.shop.exception;

import com.marco.shop.exception.auth.OtpValidationFailedException;
import com.marco.shop.exception.auth.RefreshTokenInvalidException;
import com.marco.shop.exception.customer.CustomerAlreadyExistException;
import com.marco.shop.exception.customer.CustomerNotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler({CustomerAlreadyExistException.class})
    public ResponseEntity<String> handleCustomerAlreadyExistsException(CustomerAlreadyExistException exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({OtpValidationFailedException.class})
    public ResponseEntity<Object> handleOtpValidationFailedException(OtpValidationFailedException exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<Object> handleAuthenticationException(JwtException exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RefreshTokenInvalidException.class})
    public ResponseEntity<Object> handleRefreshTokenInvalidException(RefreshTokenInvalidException exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    // unexpected exception
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleUnexpectedException(RuntimeException exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}

