package com.marco.mcshop.model.dto.order;

import com.marco.mcshop.model.constant.OrderStatus;
import com.marco.mcshop.model.entity.CustomerAddressEntity;
import com.marco.mcshop.model.entity.CustomerEntity;
import com.marco.mcshop.model.entity.OrderItemEntity;
import com.marco.mcshop.model.entity.OrderTransactionEntity;
import lombok.*;

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

    private CustomerEntity customer;

    private CustomerAddressEntity address;

    private OrderTransactionEntity transaction;

    private List<OrderItemEntity> orderItemList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
