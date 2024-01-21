package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.auth.TokenDto;
import com.marcoecommerce.shop.model.customer.CustomerLoginDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerRegisterDto;
import com.marcoecommerce.shop.service.AuthenticationService;
import com.marcoecommerce.shop.service.CustomerService;
import com.marcoecommerce.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CustomerEntity> register(@RequestBody CustomerRegisterDto customerRegisterDto) throws Exception {
        // check unique
        if (customerService.isEmailExit(customerRegisterDto.getEmail())) {
            throw new IllegalArgumentException("Duplicate email " + customerRegisterDto.getEmail());
        }

        // save customer
        CustomerEntity customerRegister = customerMapper.toEntityRegister(customerRegisterDto);
        CustomerEntity customerSaved = authenticationService.register(customerRegister);

        return ResponseEntity.ok(customerSaved);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> register(@RequestBody CustomerLoginDto customerLoginDto) {
        CustomerEntity customerLogin = customerMapper.toEntityLogin(customerLoginDto);
        CustomerEntity customer = authenticationService.login(customerLogin);

        String jwt = jwtUtil.generateToken(customer);

        return ResponseEntity.ok(TokenDto.builder().accessToken(jwt).expireAt(jwtUtil.getExpirationTime()).build());
    }

}
