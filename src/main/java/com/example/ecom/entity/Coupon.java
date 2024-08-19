package com.example.ecom.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String couponCode;

    @Column(nullable = false)
    @Range(min = 1, max = 99)
    private int discountPercent;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate issueDate;

    private LocalDate expiryDate;

    @Column(nullable = false)
    private CouponType couponType;

    private int timesUsed;
}
