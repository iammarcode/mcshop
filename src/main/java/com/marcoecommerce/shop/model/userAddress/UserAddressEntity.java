package com.marcoecommerce.shop.model.userAddress;

import com.marcoecommerce.shop.model.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = false, exclude = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_address")
@ToString(exclude = "user")
public class UserAddressEntity {
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
    @JoinColumn(name = "user_id") // join foreign key column
    private UserEntity user;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
