package com.marco.mcshop.service;

import com.marco.mcshop.model.dto.auth.TokenDto;
import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.dto.customer.CustomerLoginDto;
import com.marco.mcshop.model.dto.customer.CustomerRegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    void requestOtp(String email);

    CustomerDto register(CustomerRegisterDto customer);

    TokenDto login(CustomerLoginDto customer);

    TokenDto refreshToken(HttpServletRequest request, HttpServletResponse response);
}
