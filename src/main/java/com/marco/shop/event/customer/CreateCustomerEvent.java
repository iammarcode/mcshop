package com.marco.shop.event.customer;

import com.marco.shop.model.customer.CustomerEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateCustomerEvent extends ApplicationEvent {
    private final CustomerEntity customer;

    public CreateCustomerEvent(Object source, CustomerEntity customer) {
        super(source);
        this.customer = customer;
    }
}
