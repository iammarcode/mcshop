package com.marco.mcshop.model.dto.productCategory;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryDto {
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    //TODO: referencing/inverse side -> ProductEntity

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
