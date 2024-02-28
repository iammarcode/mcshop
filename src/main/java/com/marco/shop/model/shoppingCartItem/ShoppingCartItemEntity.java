package com.marco.shop.model.shoppingCartItem;

import com.marco.shop.model.product.ProductEntity;
import com.marco.shop.model.shoppingCart.ShoppingCartEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "shopping_cart_item")
public class ShoppingCartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCartEntity shoppingCart;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
