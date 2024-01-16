package com.marcoecommerce.shop.model.product;

import com.marcoecommerce.shop.model.productCategory.ProductCategoryEntity;
import com.marcoecommerce.shop.model.productDiscount.ProductDiscountDto;
import com.marcoecommerce.shop.model.productDiscount.ProductDiscountEntity;
import com.marcoecommerce.shop.model.productDiscountLinkEntity.ProductDiscountLinkEntity;
import com.marcoecommerce.shop.model.productInventory.ProductInventoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;

    private String name;

    private String sku;

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