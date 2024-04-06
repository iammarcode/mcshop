package com.marco.mcshop.model.dto.orderTransaction;

import com.marco.mcshop.model.constant.OrderTransactionStatus;
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

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
