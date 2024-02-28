package com.marco.shop.model.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marco.shop.model.productCategory.ProductCategoryDto;
import com.marco.shop.model.productDiscount.ProductDiscountDto;
import com.marco.shop.model.productInventory.ProductInventoryDto;
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

    private ProductCategoryDto category;

    private ProductInventoryDto inventory;

    private List<ProductDiscountDto> discountList;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deletedAt;
}