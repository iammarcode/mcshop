package com.marcoecommerce.shop.model.user;

import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import com.marcoecommerce.shop.model.userAddress.UserAddressEntity;
import com.marcoecommerce.shop.model.userPayment.UserPaymentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "shoppingCart")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @NotNull
    @Email
    private String email;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAddressEntity> addressList = new ArrayList<>();

    public void addAddress(UserAddressEntity address) {
        this.addressList.add(address);
        address.setUser(this);
    }

    public void removeAddress(UserAddressEntity address) {
        this.addressList.remove(address);
        address.setUser(null);
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserPaymentEntity> paymentList = new ArrayList<>();

    public void addPayment(UserPaymentEntity payment) {
        this.paymentList.add(payment);
        payment.setUser(this);
    }

    public void removePayment(UserPaymentEntity payment) {
        this.paymentList.remove(payment);
        payment.setUser(null);
    }

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<OrderEntity> orderList = new ArrayList<>();

    public void addOrder(OrderEntity order) {
        this.orderList.add(order);
        order.setUser(this);
    }

    public void removeOrder(OrderEntity order) {
        this.orderList.remove(order);
        order.setUser(null);
    }

    @OneToOne(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private ShoppingCartEntity shoppingCart;

    public void addShoppingCart(ShoppingCartEntity shoppingCart) {
        shoppingCart.setUser(this);
        this.setShoppingCart(shoppingCart);
    }

    public void removeShoppingCart() {
        if (this.shoppingCart != null) {
            this.shoppingCart.setUser(null);
            this.shoppingCart = null;
        }
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