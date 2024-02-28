package com.marco.shop.model.productInventory;

import com.marco.shop.model.product.ProductDto;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInventoryDto {
    private Long id;

    private String name;

    private Integer quantity;

    private ProductDto product;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
