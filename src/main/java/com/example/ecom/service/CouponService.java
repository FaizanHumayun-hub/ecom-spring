package com.example.ecom.service;

import com.example.ecom.dtos.coupon.CouponRequest;
import com.example.ecom.entity.Coupon;
import com.example.ecom.mapper.CouponMapper;
import com.example.ecom.repository.CouponRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CouponService {
    private CouponRepository couponRepository;
    private CouponMapper couponMapper;

    public Coupon createCoupon(CouponRequest couponRequest) {
        return couponRepository.save(couponMapper.toEntity(couponRequest));
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getCouponByCode(String code) {
        return couponRepository.findByCouponCode(code);
    }
}
