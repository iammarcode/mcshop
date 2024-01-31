package com.marcoecommerce.shop.event.customer;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateCustomerEvent extends ApplicationEvent {
    private final CustomerEntity message;

    public CreateCustomerEvent(Object source, CustomerEntity message) {
        super(source);
        this.message = message;
    }
}
