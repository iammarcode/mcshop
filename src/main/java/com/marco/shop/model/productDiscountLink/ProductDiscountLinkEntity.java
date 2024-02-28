package com.marco.shop.model.productDiscountLink;

import com.marco.shop.model.product.ProductEntity;
import com.marco.shop.model.productDiscount.ProductDiscountEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "product_discount_link")
public class ProductDiscountLinkEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private ProductDiscountEntity discount;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}