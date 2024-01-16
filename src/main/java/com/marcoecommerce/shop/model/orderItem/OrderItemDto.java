package com.marcoecommerce.shop.model.orderItem;

import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.product.ProductEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
