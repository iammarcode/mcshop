package com.marcoecommerce.shop.model.order;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.model.basic.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "CUSTOMER_ID_FK"))
    private CustomerEntity customer;


}
