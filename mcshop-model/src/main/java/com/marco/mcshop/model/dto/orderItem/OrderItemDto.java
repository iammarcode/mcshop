package com.marco.mcshop.model.dto.orderItem;

import com.marco.mcshop.model.dto.order.OrderDto;
import com.marco.mcshop.model.entity.ProductEntity;
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
