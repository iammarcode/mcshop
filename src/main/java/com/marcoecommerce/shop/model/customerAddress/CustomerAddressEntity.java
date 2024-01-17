package com.marcoecommerce.shop.model.customerAddress;

import com.marcoecommerce.shop.model.customer.CustomerEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false, exclude = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer_address")
@ToString(exclude = "customer")
public class CustomerAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "postal_code")
    @NotNull
    private String postalCode;

    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "country")
    @NotNull
    private String country;

    @Column(name = "phone")
    @NotNull
    private String phone;

    // owning side
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id") // join foreign key column
    private CustomerEntity customer;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
