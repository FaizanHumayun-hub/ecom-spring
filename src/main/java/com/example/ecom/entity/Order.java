package com.example.ecom.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private UUID orderId;

    @NotNull(message = "User must be specified, order must belong to a user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Whats the point of order if it doesnt have any product?")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="orders_products",
            joinColumns={@JoinColumn(name="orderId")},
            inverseJoinColumns={@JoinColumn(name="productId")})
    private Set<Product> products = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon ;
}
