package com.marco.shop.event.customer;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RequestOtpCustomerEvent extends ApplicationEvent {
    private final String otp;
    private final String email;

    public RequestOtpCustomerEvent(Object source, String otp, String email) {
        super(source);
        this.otp = otp;
        this.email = email;
    }
}
