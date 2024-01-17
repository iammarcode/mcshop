package com.marcoecommerce.shop.model.shoppingCart;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDto {
    private Long id;

    private BigDecimal total;

    private CustomerEntity customer;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
