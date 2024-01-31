package com.marcoecommerce.shop.event.customer.listener;

import com.marcoecommerce.shop.event.customer.CreateCustomerEvent;
import com.marcoecommerce.shop.event.customer.RequestOtpCustomerEvent;
import com.marcoecommerce.shop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventListener {

    @Autowired
    private EmailService emailService;

    @EventListener
    public void handleCustomerRequestOtp(RequestOtpCustomerEvent event) {
        emailService.sendSimpleMessage(
                event.getEmail(),
                "One-time password",
                event.getOtp()
        );
    }

    @EventListener
    public void handleCreateCustomer(CreateCustomerEvent event) {
        emailService.sendSimpleMessage(
                event.getCustomer().getEmail(),
                "Registration Successfully",
                "Welcome To MC-Shop"
        );
    }
}
