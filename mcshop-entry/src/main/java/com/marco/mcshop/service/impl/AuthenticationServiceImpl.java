package com.marco.mcshop.service.impl;

import com.marco.mcshop.exception.auth.OtpValidationFailedException;
import com.marco.mcshop.exception.auth.RefreshTokenInvalidException;
import com.marco.mcshop.exception.customer.CustomerAlreadyExistException;
import com.marco.mcshop.model.dto.CustomerDto;
import com.marco.mcshop.model.entity.Customer;
import com.marco.mcshop.model.entity.ShoppingCart;
import com.marco.mcshop.model.mapper.impl.CustomerMapper;
import com.marco.mcshop.model.repository.CustomerRepository;
import com.marco.mcshop.payload.request.LoginRequest;
import com.marco.mcshop.payload.request.RegisterRequest;
import com.marco.mcshop.payload.response.TokenResponse;
import com.marco.mcshop.service.AuthenticationService;
import com.marco.mcshop.service.CustomerService;
import com.marco.mcshop.service.EmailService;
import com.marco.mcshop.service.OtpService;
import com.marco.mcshop.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final OtpService otpService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository, CustomerService customerService, OtpService otpService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.otpService = otpService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

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
    public CustomerDto register(RegisterRequest registerRequest) {
        // check otp
        String otpCached = otpService.getOtpByKey(registerRequest.getEmail());
        if (!registerRequest.getOtp().equals(otpCached)) {
            throw new OtpValidationFailedException(registerRequest.getOtp());
        }

        // hash password
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // create customer
        Customer customerRegister = Customer.builder().email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .nickname(registerRequest.getNickname())
                .build();

        // create an empty shoppingCart
        ShoppingCart shoppingCart = ShoppingCart.builder().total(BigDecimal.valueOf(0)).build();
        customerRegister.addShoppingCart(shoppingCart);

        Customer customerSaved = customerRepository.save(customerRegister);

        // clear otp cache
        otpService.clearOtpByKey(customerRegister.getEmail());

        // send registration email
        emailService.sendSimpleMessage(
                customerSaved.getEmail(),
                "Welcome To MCShop, " + customerSaved.getNickname(),
                "Registration Successfully"
        );

        return customerMapper.toDto(customerSaved);
    }

    @Override
    public TokenResponse login(LoginRequest customer) {
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

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessExpireAt(jwtUtil.getAccessExpirationTime())
                .refreshToken(refreshToken)
                .refreshExpireAt(jwtUtil.getRefreshExpirationTime())
                .build();
    }

    @Override
    public TokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RefreshTokenInvalidException(authHeader);
        }

        String refreshToken = authHeader.substring(7);
        String email = jwtUtil.getUsername(refreshToken);
        if (email != null) {
            UserDetails customer = customerService.findUserDetailsByEmail(email);

            if (jwtUtil.isTokenValid(refreshToken, customer)) {
                String newAccessToken = jwtUtil.generateAccessToken(customer);
                String newRefreshToken = jwtUtil.generateRefreshToken(customer);

                return TokenResponse.builder()
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
