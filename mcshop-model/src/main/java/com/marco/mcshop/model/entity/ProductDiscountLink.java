package com.marco.mcshop.model.entity;

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
public class ProductDiscountLink {

    @Id
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private ProductDiscount discount;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
