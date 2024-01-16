package com.marcoecommerce.shop.model.orderTransaction;

import com.marcoecommerce.shop.model.order.OrderEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTransactionDto {
    private Long id;

    private OrderTransactionStatus status;

    private BigDecimal amount;

    private String provider;

    private String accountNo;

    private OrderEntity order;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
