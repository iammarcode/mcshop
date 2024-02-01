package com.marcoecommerce.shop.service.impl;

import com.marcoecommerce.shop.event.customer.CreateCustomerEvent;
import com.marcoecommerce.shop.event.customer.RequestOtpCustomerEvent;
import com.marcoecommerce.shop.exception.auth.OtpValidationFailedException;
import com.marcoecommerce.shop.exception.auth.RefreshTokenInvalidException;
import com.marcoecommerce.shop.exception.customer.CustomerAlreadyExistException;
import com.marcoecommerce.shop.exception.customer.CustomerNotFoundException;
import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.auth.TokenDto;
import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customer.CustomerLoginDto;
import com.marcoecommerce.shop.model.customer.CustomerRegisterDto;
import com.marcoecommerce.shop.repository.CustomerRepository;
import com.marcoecommerce.shop.service.AuthenticationService;
import com.marcoecommerce.shop.service.OtpService;
import com.marcoecommerce.shop.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void requestOtp(String email) {
        // check email
        boolean existsByEmail = customerRepository.existsByEmail(email);
        if (existsByEmail) {
            throw new CustomerAlreadyExistException(email);
        }

        String otp = otpService.generateOTP(email);

        // event
        eventPublisher.publishEvent(new RequestOtpCustomerEvent(eventPublisher, otp, email));
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

        // event
        eventPublisher.publishEvent(new CreateCustomerEvent(eventPublisher ,customerSaved));

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

        Optional<CustomerEntity> customerFound = customerRepository.findByEmail(customer.getEmail());
        if (customerFound.isEmpty()) {
            log.error("Customer not found with email: " + customer.getEmail());
            throw new CustomerNotFoundException(customer.getEmail());
        }

        String accessToken = jwtUtil.generateAccessToken(customerFound.get());
        String refreshToken = jwtUtil.generateRefreshToken(customerFound.get());

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
            CustomerEntity customer = customerRepository.findByEmail(email).orElseThrow(
                    () -> {
                        log.error("Customer not found with email: " + email);
                        return new CustomerNotFoundException(email);
                    }
            );
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
