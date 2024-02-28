package com.marco.shop.model.orderTransaction;

import com.marco.shop.model.order.OrderEntity;
import lombok.*;

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
