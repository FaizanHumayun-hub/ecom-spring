package com.example.ecom.dtos.coupon;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponRequest {

    private String couponCode;
    private int discountPercent;
    private String couponType;
    private int timesUsed;
    private LocalDate expiryDate;
}
