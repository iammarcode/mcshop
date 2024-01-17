package com.marcoecommerce.shop.model.customerAddress;

import com.marcoecommerce.shop.model.customer.CustomerDto;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddressDto {
    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String postalCode;

    private String city;

    private String country;

    private String phone;

    private CustomerDto customer;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
