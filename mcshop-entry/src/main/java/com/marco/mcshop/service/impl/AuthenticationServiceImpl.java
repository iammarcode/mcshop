package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.auth.OtpValidationFailedException;
import com.marco.mcshop.exception.auth.RefreshTokenInvalidException;
import com.marco.mcshop.exception.customer.CustomerAlreadyExistException;
import com.marco.mcshop.service.AuthenticationService;
import com.marco.mcshop.service.CustomerService;
import com.marco.mcshop.service.EmailService;
import com.marco.mcshop.service.OtpService;
import com.marco.mcshop.util.JwtUtil;
import com.marco.mcshop.model.dto.auth.TokenDto;
import com.marco.mcshop.model.dto.customer.CustomerDto;
import com.marco.mcshop.model.dto.customer.CustomerLoginDto;
import com.marco.mcshop.model.dto.customer.CustomerRegisterDto;
import com.marco.mcshop.model.entity.CustomerEntity;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.model.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public void requestOtp(String email) {
        // check email
        boolean existsByEmail = customerRepository.existsByEmail(email);
        if (existsByEmail) {
            throw new CustomerAlreadyExistException(email);
        }

        // send otp email
        String otp = otpService.generateOTP(email);
        emailService.sendSimpleMessage(email, "One-time password", otp);
    }

    @Override
    public CustomerDto register(CustomerRegisterDto customer) {
        // check otp
        String otpCached = otpService.getOtpByKey(customer.getEmail());
        if (!customer.getOtp().equals(otpCached)) {
            throw new OtpValidationFailedException(customer.getOtp());
        }

        // hash password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        // create customer
        CustomerEntity customerRegister = customerMapper.toEntityRegister(customer);
        CustomerEntity customerSaved = customerRepository.save(customerRegister);

        // clear otp cache
        otpService.clearOtpByKey(customer.getEmail());

        // send registration email
        emailService.sendSimpleMessage(
                customerSaved.getEmail(),
                "Welcome To MCShop, " + customerSaved.getNickname(),
                "Registration Successfully"
        );

        return customerMapper.toDto(customerSaved);
    }

    @Override
    public TokenDto login(CustomerLoginDto customer) {
        // TODO: ?
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customer.getEmail(),
                        customer.getPassword()
                )
        );

        UserDetails customerFound = customerService.findUserDetailsByEmail(customer.getEmail());


        String accessToken = jwtUtil.generateAccessToken(customerFound);
        String refreshToken = jwtUtil.generateRefreshToken(customerFound);

        return TokenDto.builder()
                .accessToken(accessToken)
                .accessExpireAt(jwtUtil.getAccessExpirationTime())
                .refreshToken(refreshToken)
                .refreshExpireAt(jwtUtil.getRefreshExpirationTime())
                .build();
    }

    @Override
    public TokenDto refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Invalid refresh token header: " + authHeader);
            throw new RefreshTokenInvalidException(authHeader);
        }

        String refreshToken = authHeader.substring(7);
        String email = jwtUtil.getUsername(refreshToken);
        if (email != null) {
            UserDetails customer = customerService.findUserDetailsByEmail(email);

            if (jwtUtil.isTokenValid(refreshToken, customer)) {
                String newAccessToken = jwtUtil.generateAccessToken(customer);
                String newRefreshToken = jwtUtil.generateRefreshToken(customer);

                return TokenDto.builder()
                        .refreshToken(newRefreshToken)
                        .refreshExpireAt(jwtUtil.getRefreshExpirationTime())
                        .accessToken(newAccessToken)
                        .accessExpireAt(jwtUtil.getAccessExpirationTime())
                        .build();
            }
        }
        throw new RefreshTokenInvalidException(refreshToken);
    }
}
