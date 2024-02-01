package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.auth.TokenDto;
import com.marcoecommerce.shop.model.customer.*;
import com.marcoecommerce.shop.service.AuthenticationService;
import com.marcoecommerce.shop.service.CustomerService;
import com.marcoecommerce.shop.service.EmailService;
import com.marcoecommerce.shop.service.OtpService;
import com.marcoecommerce.shop.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<String> requestOtp(@RequestBody OtpDto otpDto) throws Exception {
        authenticationService.requestOtp(otpDto.getEmail());

        return new ResponseEntity<>("Request OTP Successfully" ,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerRegisterDto customerRegisterDto) throws Exception {
        CustomerDto customerDto = authenticationService.register(customerRegisterDto);

        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody CustomerLoginDto customerLoginDto) {
        TokenDto tokenDto = authenticationService.login(customerLoginDto);

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        TokenDto tokenDto = authenticationService.refreshToken(request, response);

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }
}
