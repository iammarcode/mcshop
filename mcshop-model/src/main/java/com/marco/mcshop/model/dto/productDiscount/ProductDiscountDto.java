package com.marco.mcshop.model.dto.productDiscount;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDiscountDto {
    private Long id;

    private String name;

    private String description;

    private BigDecimal discountPercent;

    private Boolean active;

    //TODO: referencing/inverse side -> ProductEntity

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
