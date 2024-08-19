package com.example.ecom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

import java.util.UUID;

@Entity
@Data
@Table(name = "products")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private UUID id;

    @Column(nullable = false)
    @Size(min = 5, max = 50, message = "name must be between 5 and 50 characters long")
    private String name;

    @Column(nullable = false)
    @NotNull
    @Range(min = 1, max = 999999999, message = "Price cannot be negative or greater than 999999999")
    private int price;

    @Column(nullable = false)
    @NotNull(message = "quantity cannot be null")
    @Range(min = 0, max = 100000, message = "quantity must not be greater than 100,000 or less than 0")
    private int quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull(message = "User cannot be null, product must have an owner")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
