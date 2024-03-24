package com.marco.mcshop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "shoppingCart")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @NotEmpty
    private String email;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CustomerAddressEntity> addressList = new ArrayList<>();

    public void addAddress(CustomerAddressEntity address) {
        this.addressList.add(address);
        address.setCustomer(this);
    }

    public void removeAddress(CustomerAddressEntity address) {
        this.addressList.remove(address);
        address.setCustomer(null);
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CustomerPaymentEntity> paymentList = new ArrayList<>();

    public void addPayment(CustomerPaymentEntity payment) {
        this.paymentList.add(payment);
        payment.setCustomer(this);
    }

    public void removePayment(CustomerPaymentEntity payment) {
        this.paymentList.remove(payment);
        payment.setCustomer(null);
    }

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<OrderEntity> orderList = new ArrayList<>();

    public void addOrder(OrderEntity order) {
        this.orderList.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(OrderEntity order) {
        this.orderList.remove(order);
        order.setCustomer(null);
    }

    @OneToOne(
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private ShoppingCartEntity shoppingCart;

    public void addShoppingCart(ShoppingCartEntity shoppingCart) {
        shoppingCart.setCustomer(this);
        this.setShoppingCart(shoppingCart);
    }

    public void removeShoppingCart() {
        if (this.shoppingCart != null) {
            this.shoppingCart.setCustomer(null);
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