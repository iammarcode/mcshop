package com.marcoecommerce.shop.event.customer;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
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
