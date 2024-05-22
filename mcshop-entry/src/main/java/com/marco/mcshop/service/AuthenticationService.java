package com.marco.mcshop.service;

import com.marco.mcshop.payload.response.TokenResponse;
import com.marco.mcshop.model.dto.CustomerDto;
import com.marco.mcshop.payload.request.LoginRequest;
import com.marco.mcshop.payload.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    void requestOtp(String email);

    CustomerDto register(RegisterRequest customer);

    TokenResponse login(LoginRequest customer);

    TokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
}
