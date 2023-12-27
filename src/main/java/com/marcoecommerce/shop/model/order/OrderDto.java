package com.marcoecommerce.shop.model.order;

import com.marcoecommerce.shop.model.customer.CustomerDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;

    private OrderStatus status;

    private CustomerDto customer;
}
