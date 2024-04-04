package com.marco.mcshop.exception;

import com.marco.mcshop.exception.auth.OtpValidationFailedException;
import com.marco.mcshop.exception.auth.RefreshTokenInvalidException;
import com.marco.mcshop.exception.customer.CustomerAlreadyExistException;
import com.marco.mcshop.exception.customer.CustomerNotFoundException;
import com.marco.mcshop.exception.shoppingCart.CartItemNotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        log.error(String.valueOf(exception));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler({CustomerAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleCustomerAlreadyExistsException(CustomerAlreadyExistException exception) {
        log.error(String.valueOf(exception));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({OtpValidationFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleOtpValidationFailedException(OtpValidationFailedException exception) {
        log.error(String.valueOf(exception));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({JwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAuthenticationException(JwtException exception) {
        log.error(String.valueOf(exception));

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RefreshTokenInvalidException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleRefreshTokenInvalidException(RefreshTokenInvalidException exception) {
        log.error(String.valueOf(exception));

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler({CartItemNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleCartItemNotFoundException(CartItemNotFoundException exception) {
        log.error(String.valueOf(exception));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    // unexpected exception
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleUnexpectedException(RuntimeException exception) {
        log.error(String.valueOf(exception));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}

