package com.marco.shop.model.orderItem;

import com.marco.shop.model.order.OrderDto;
import com.marco.shop.model.product.ProductEntity;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;

    private Integer quantity;

    private OrderDto order;

    private ProductEntity product;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
