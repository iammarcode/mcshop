package com.marcoecommerce.shop.model.userPayment;

import com.marcoecommerce.shop.model.user.UserDto;
import com.marcoecommerce.shop.model.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPaymentDto {
    private Long id;

    private UserPaymentType type;

    private String provider;

    private String accountNo;

    private LocalDate expiry;

    private UserDto user;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
