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
@Entity
@Table(name = "product_discount")
public class ProductDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @NotNull
    private String type;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @Column(name = "value")
    @NotNull
    private BigDecimal value;

    @Column(name = "expiry")
    private LocalDateTime expiry;

    @OneToMany(
            mappedBy = "discount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductDiscountLink> productLinkList = new ArrayList<>();

    public void addProduct(Product product) {
        ProductDiscountLink link = ProductDiscountLink.builder()
                .discount(this)
                .product(product)
                .build();

        this.productLinkList.add(link);
        product.getDiscountLinkList().add(link);
    }

    public void removeProduct(Product product) {
        ProductDiscountLink link = this.productLinkList.stream()
                .filter(e -> e.getProduct() == product && e.getDiscount() == this)
                .findFirst()
                .orElse(null);



        if (link != null) {
            // remove link from product and discount
            this.productLinkList.remove(link);
            product.getDiscountLinkList().remove(link);

            // remove product and discount from link table
            link.setDiscount(null);
            link.setProduct(null);
        }
    }

    public List<Product> getProductList() {
        return this.productLinkList
                .stream()
                .map(ProductDiscountLink::getProduct)
                .collect(Collectors.toList());
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
