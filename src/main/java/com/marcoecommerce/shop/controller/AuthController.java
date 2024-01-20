package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.auth.TokenResponse;
import com.marcoecommerce.shop.model.auth.LoginRequest;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.auth.RegisterRequest;
import com.marcoecommerce.shop.service.AuthenticationService;
import com.marcoecommerce.shop.service.CustomerService;
import com.marcoecommerce.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerEntity> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        // check unique
        if (customerService.isEmailExit(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Duplicate email " + registerRequest.getEmail());
        }

        // save customer
        CustomerEntity customerRegister = customerMapper.toEntityRegister(registerRequest);
        CustomerEntity customerSaved = authenticationService.register(customerRegister);

        return ResponseEntity.ok(customerSaved);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> register(@RequestBody LoginRequest loginRequest) {
        CustomerEntity customerLogin = customerMapper.toEntityLogin(loginRequest);
        CustomerEntity customer = authenticationService.login(customerLogin);

        String jwt = jwtUtil.generateToken(customer);

        return ResponseEntity.ok(TokenResponse.builder().accessToken(jwt).expire_at(jwtUtil.getExpirationTime()).build());
    }

}
