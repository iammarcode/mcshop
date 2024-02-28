package com.marco.shop.service;

import com.marco.shop.model.auth.TokenDto;
import com.marco.shop.model.customer.CustomerDto;
import com.marco.shop.model.customer.CustomerLoginDto;
import com.marco.shop.model.customer.CustomerRegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    void requestOtp(String email);

    CustomerDto register(CustomerRegisterDto customer);

    TokenDto login(CustomerLoginDto customer);

    TokenDto refreshToken(HttpServletRequest request, HttpServletResponse response);
}
