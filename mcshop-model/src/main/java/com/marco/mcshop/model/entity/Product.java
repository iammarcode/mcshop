package com.marco.mcshop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Product {
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
    private ProductCategory category;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductDiscountLink> discountLinkList = new ArrayList<>();

    public void addAssociationDiscount(ProductDiscount discount) {
        ProductDiscountLink productDiscountLink = ProductDiscountLink.builder()
                .discount(discount)
                .product(this)
                .build();

        this.discountLinkList.add(productDiscountLink);
        discount.getProductLinkList().add(productDiscountLink);
    }

    public void removeAssociationDiscount(ProductDiscount discount) {
        ProductDiscountLink link = this.discountLinkList
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

    public List<ProductDiscount> getDiscountList() {
        return this.discountLinkList
                .stream()
                .map(ProductDiscountLink::getDiscount)
                .collect(Collectors.toList());
    }

    @OneToOne(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private ProductInventory inventory;

    public void addAssociationInventory(ProductInventory inventory) {
        inventory.setProduct(this);
        this.inventory = inventory;
    }

    public void removeAssociationInventory() {
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
    private List<OrderItem> orderItemList = new ArrayList<>();

    public void addAssociationOrderItem(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
        orderItem.setProduct(this);
    }

    public void removeAssociationOrderItem(OrderItem orderItem) {
        this.orderItemList.remove(orderItem);
        orderItem.setProduct(null);
    }

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();

    public void addAssociationCartItem(ShoppingCartItem shoppingCartItem) {
        this.shoppingCartItemList.add(shoppingCartItem);
        shoppingCartItem.setProduct(this);
    }

    public void removeAssociationCartItem(ShoppingCartItem shoppingCartItem) {
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