package com.marcoecommerce.shop.model.order;

import com.marcoecommerce.shop.model.orderItem.OrderItemEntity;
import com.marcoecommerce.shop.model.orderTransaction.OrderTransactionEntity;
import com.marcoecommerce.shop.model.user.UserEntity;
import com.marcoecommerce.shop.model.userAddress.UserAddressEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;

    private OrderStatus status;

    private BigDecimal total;

    private UserEntity user;

    private UserAddressEntity address;

    private OrderTransactionEntity transaction;

    private List<OrderItemEntity> orderItemList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
