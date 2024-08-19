package com.example.ecom.controller;

import com.example.ecom.dtos.coupon.CouponRequest;
import com.example.ecom.entity.Coupon;
import com.example.ecom.service.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
@AllArgsConstructor
public class CouponController {

    private CouponService couponService;

    @PostMapping
    public ResponseEntity<?> addCoupon(@RequestBody CouponRequest coupon) {
        return new ResponseEntity<>(couponService.createCoupon(coupon), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(couponService.getAllCoupons(), HttpStatus.OK);
    }
}
