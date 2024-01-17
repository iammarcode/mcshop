package com.marcoecommerce.shop.model.order;

import com.marcoecommerce.shop.model.orderItem.OrderItemEntity;
import com.marcoecommerce.shop.model.orderTransaction.OrderTransactionEntity;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.customerAddress.CustomerAddressEntity;
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
