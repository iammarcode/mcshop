package com.marco.mcshop.model.entity;

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
@Entity(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity category;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductDiscountLinkEntity> discountLinkList = new ArrayList<>();

    public void addDiscount(ProductDiscountEntity discount) {
        ProductDiscountLinkEntity productDiscountLink = ProductDiscountLinkEntity.builder()
                .discount(discount)
                .product(this)
                .build();

        this.discountLinkList.add(productDiscountLink);
        discount.getProductLinkList().add(productDiscountLink);
    }

    public void removeDiscount(ProductDiscountEntity discount) {
        ProductDiscountLinkEntity link = this.discountLinkList
                .stream()
                .filter(e -> e.getDiscount() == discount && e.getProduct() == this)
                .findFirst()
                .orElse(null);

        if (link != null) {
            // remove link from product and discount
            this.discountLinkList.remove(link);
            discount.getProductLinkList().remove(link);

            // remove product and discount from link table
            link.setProduct(null);
            link.setDiscount(null);
        }
    }

    public List<ProductDiscountEntity> getDiscountList() {
        return this.discountLinkList
                .stream()
                .map(ProductDiscountLinkEntity::getDiscount)
                .collect(Collectors.toList());
    }

    @OneToOne(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private ProductInventoryEntity inventory;

    public void addInventory(ProductInventoryEntity inventory) {
        inventory.setProduct(this);
        this.inventory = inventory;
    }

    public void removeInventory() {
        if (this.inventory != null) {
            inventory.setProduct(null);
            this.inventory = null;
        }
    }

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<OrderItemEntity> orderItemList = new ArrayList<>();

    public void addOrderItem(OrderItemEntity orderItem) {
        this.orderItemList.add(orderItem);
        orderItem.setProduct(this);
    }

    public void removeOrderItem(OrderItemEntity orderItem) {
        this.orderItemList.remove(orderItem);
        orderItem.setProduct(null);
    }

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ShoppingCartItemEntity> shoppingCartItemList = new ArrayList<>();

    public void addShoppingCartItem(ShoppingCartItemEntity shoppingCartItem) {
        this.shoppingCartItemList.add(shoppingCartItem);
        shoppingCartItem.setProduct(this);
    }

    public void removeShoppingCartItem(ShoppingCartItemEntity shoppingCartItem) {
        this.shoppingCartItemList.remove(shoppingCartItem);
        shoppingCartItem.setProduct(null);
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