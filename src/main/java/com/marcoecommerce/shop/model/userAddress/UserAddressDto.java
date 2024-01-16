package com.marcoecommerce.shop.model.userAddress;

import com.marcoecommerce.shop.model.user.UserDto;
import com.marcoecommerce.shop.model.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddressDto {
    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String postalCode;

    private String city;

    private String country;

    private String phone;

    private UserDto user;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
