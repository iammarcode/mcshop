package com.marco.mcshop.model.dto;

import com.marco.mcshop.model.constant.CustomerPaymentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPaymentDto {
    private Long id;

    private CustomerPaymentType type;

    private String provider;

    private String accountNo;

    private LocalDate expiry;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
