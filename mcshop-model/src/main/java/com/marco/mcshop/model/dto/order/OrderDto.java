package com.marco.mcshop.model.dto.order;

import com.marco.mcshop.model.constant.OrderStatus;
import com.marco.mcshop.model.dto.customerAddress.CustomerAddressDto;
import com.marco.mcshop.model.dto.orderItem.OrderItemDto;
import com.marco.mcshop.model.dto.orderTransaction.OrderTransactionDto;
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

    private CustomerAddressDto address;

    private OrderTransactionDto transaction;

    private List<OrderItemDto> orderItemList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
