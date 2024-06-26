package com.marco.mcshop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "shopping_cart")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    @NotNull
    private BigDecimal total;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private CustomerEntity customer;

    @OneToMany(
            mappedBy = "shoppingCart",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<ShoppingCartItemEntity> shoppingCartItemList;

    public void addAssociationCartItem(ShoppingCartItemEntity shoppingCartItem) {
        this.shoppingCartItemList.add(shoppingCartItem);
        shoppingCartItem.setShoppingCart(this);
    }

    public void removeAssociationCartItem(ShoppingCartItemEntity shoppingCartItem) {
        this.shoppingCartItemList.remove(shoppingCartItem);
        shoppingCartItem.setShoppingCart(null);
    }

    public void clearAssociationCartItem() {
        this.shoppingCartItemList.clear();
    }

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
