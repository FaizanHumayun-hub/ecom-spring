package com.example.ecom.mapper;

import com.example.ecom.dtos.coupon.CouponRequest;
import com.example.ecom.entity.Coupon;
import com.example.ecom.entity.CouponType;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {
    public Coupon toEntity(CouponRequest couponRequest) {
        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponRequest.getCouponCode());
        coupon.setCouponType(CouponType.valueOf(couponRequest.getCouponType()));
        coupon.setDiscountPercent(couponRequest.getDiscountPercent());
        coupon.setTimesUsed(couponRequest.getTimesUsed());
        coupon.setExpiryDate(couponRequest.getExpiryDate());
        return coupon;
    }
}
