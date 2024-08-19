package com.example.ecom.repository;

import com.example.ecom.entity.Coupon;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Id> {
    Coupon findByCouponCode(String couponCode);
}
