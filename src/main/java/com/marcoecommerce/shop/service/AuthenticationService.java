package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.auth.TokenDto;
import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerLoginDto;
import com.marcoecommerce.shop.model.customer.CustomerRegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    void requestOtp(String email);

    CustomerDto register(CustomerRegisterDto customer);

    TokenDto login(CustomerLoginDto customer);

    TokenDto refreshToken(HttpServletRequest request, HttpServletResponse response);
}
