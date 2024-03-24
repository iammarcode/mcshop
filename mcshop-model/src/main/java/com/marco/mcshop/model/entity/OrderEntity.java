package com.marco.mcshop.model.entity;

import com.marco.mcshop.model.constant.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private OrderStatus status;

    @Column(name = "total")
    @NotNull
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_address_id")
    private CustomerAddressEntity address;

    @OneToOne(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private OrderTransactionEntity transaction;

    public void addTransaction(OrderTransactionEntity transaction) {
        transaction.setOrder(this);
        this.setTransaction(transaction);
    }

    public void removeTransaction() {
        transaction.setOrder(null);
        this.setTransaction(null);
    }

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<OrderItemEntity> orderItemList = new ArrayList<>();

    public void addOrderItem(OrderItemEntity orderItem) {
        this.orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItemEntity orderItem) {
        this.orderItemList.remove(orderItem);
        orderItem.setOrder(null);
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
