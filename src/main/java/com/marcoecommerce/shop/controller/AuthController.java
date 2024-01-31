package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.auth.TokenDto;
import com.marcoecommerce.shop.model.customer.*;
import com.marcoecommerce.shop.service.AuthenticationService;
import com.marcoecommerce.shop.service.CustomerService;
import com.marcoecommerce.shop.service.EmailService;
import com.marcoecommerce.shop.service.OtpService;
import com.marcoecommerce.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;
    @GetMapping("/otp")
    public ResponseEntity<CustomerDto> requestOtp(@RequestBody OtpDto otpDto) throws Exception {
        authenticationService.requestOtp(otpDto.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerRegisterDto customerRegisterDto) throws Exception {
        CustomerEntity customerSaved = authenticationService.register(customerRegisterDto);

        return new ResponseEntity<>(customerMapper.toDto(customerSaved), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> register(@RequestBody CustomerLoginDto customerLoginDto) {
        CustomerEntity customer = authenticationService.login(customerLoginDto);

        String jwt = jwtUtil.generateToken(customer);

        return new ResponseEntity<>(TokenDto.builder().accessToken(jwt).expireAt(jwtUtil.getExpirationTime()).build(), HttpStatus.OK);
    }

    // TODO: refresh token
}
