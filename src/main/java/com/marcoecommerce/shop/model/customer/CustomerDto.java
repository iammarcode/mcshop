package com.marcoecommerce.shop.model.customer;

import com.marcoecommerce.shop.model.order.OrderDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Long id;

    private String name;

    private List<OrderDto> orders = new ArrayList<>();
}
