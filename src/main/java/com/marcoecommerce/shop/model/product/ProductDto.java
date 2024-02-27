package com.marcoecommerce.shop.model.product;

import com.marcoecommerce.shop.model.productCategory.ProductCategoryEntity;
import com.marcoecommerce.shop.model.productDiscount.ProductDiscountDto;
import com.marcoecommerce.shop.model.productInventory.ProductInventoryEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private String imageUrl;

    private ProductCategoryEntity category;

    private ProductInventoryEntity inventory;

    private List<ProductDiscountDto> discountList;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}