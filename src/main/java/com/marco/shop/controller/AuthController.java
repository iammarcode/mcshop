package com.marco.shop.controller;

import com.marco.shop.model.auth.TokenDto;
import com.marco.shop.model.customer.CustomerDto;
import com.marco.shop.model.customer.CustomerLoginDto;
import com.marco.shop.model.customer.CustomerRegisterDto;
import com.marco.shop.model.customer.OtpDto;
import com.marco.shop.publisher.CustomerPublisher;
import com.marco.shop.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CustomerPublisher customerPublisher;

    @GetMapping("/otp")
    public ResponseEntity<String> requestOtp(@RequestBody OtpDto otpDto) throws Exception {
        customerPublisher.publishOtp(otpDto.getEmail());

        return ResponseEntity.ok("Request OTP Successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerRegisterDto customerRegisterDto) throws Exception {
        customerPublisher.publishRegister(customerRegisterDto);

        //TODO: polling api result from queue

        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody CustomerLoginDto customerLoginDto) {
        TokenDto tokenDto = authenticationService.login(customerLoginDto);

        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        TokenDto tokenDto = authenticationService.refreshToken(request, response);

        return ResponseEntity.ok(tokenDto);
    }
}
