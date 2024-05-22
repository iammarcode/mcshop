package com.marco.mcshop.controller;

import com.marco.mcshop.payload.response.TokenResponse;
import com.marco.mcshop.model.dto.CustomerDto;
import com.marco.mcshop.payload.request.LoginRequest;
import com.marco.mcshop.payload.request.RegisterRequest;
import com.marco.mcshop.payload.request.OtpRequest;
import com.marco.mcshop.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/otp")
    public ResponseEntity<String> otp(@RequestBody OtpRequest otpRequest) throws Exception {
        authenticationService.requestOtp(otpRequest.getEmail());

        return ResponseEntity.ok("Request OTP Successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        CustomerDto registeredCustomer = authenticationService.register(registerRequest);

        return ResponseEntity.ok(registeredCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authenticationService.login(loginRequest);

        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        TokenResponse tokenResponse = authenticationService.refreshToken(request, response);

        return ResponseEntity.ok(tokenResponse);
    }
}
