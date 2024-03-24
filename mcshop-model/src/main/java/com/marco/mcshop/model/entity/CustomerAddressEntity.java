package com.marco.mcshop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class CustomerAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "postal_code")
    @NotBlank
    private String postalCode;

    @Column(name = "city")
    @NotBlank
    private String city;

    @Column(name = "country")
    @NotBlank
    private String country;

    @Column(name = "phone")
    @NotBlank
    private String phone;

    // owning side
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
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
