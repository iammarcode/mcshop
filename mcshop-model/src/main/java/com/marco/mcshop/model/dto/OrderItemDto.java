package com.marco.mcshop.model.dto;

import com.marco.mcshop.model.dto.OrderDto;
import com.marco.mcshop.model.entity.Product;
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

    private Product product;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
