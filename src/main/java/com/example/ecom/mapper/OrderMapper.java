package com.example.ecom.mapper;

import com.example.ecom.dtos.order.OrderRequest;
import com.example.ecom.dtos.order.OrderResponse;
import com.example.ecom.dtos.product.ProductResponseOrder;
import com.example.ecom.entity.*;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.service.CouponService;
import com.example.ecom.service.ProductService;
import com.example.ecom.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class OrderMapper {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final CouponService couponService;

    public OrderMapper(UserService userService, ProductService productService, CouponService couponService, UserRepository userRepository) {
        this.userService = userService;
        this.productService = productService;
        this.couponService = couponService;
        this.userRepository = userRepository;
    }

    public  Order toEntity(OrderRequest orderRequest){
        User user = userService.getFullUserById(orderRequest.getUserId());
        Coupon coupon = couponService.getCouponByCode(orderRequest.getCouponCode());
        Set<Product> productSet  = new HashSet<>();
        for (UUID product : orderRequest.getProductId()){
            productSet.add(productService.getFullProductById(product));
        }
        Order order = new Order();
        order.setUser(user);
        order.setOrderId(UUID.randomUUID());
        order.setProducts(productSet);
//        if(order.getCoupon() != null) {
//            if( user.getCouponUsed() !=null && coupon.getCouponType() == CouponType.SINGLE && user.getCouponUsed().contains(coupon)){
//                throw new IllegalArgumentException("This coupon has already been used by the user.");
//            }
//            assert user.getCouponUsed() != null;
//            user.getCouponUsed().add(coupon);
//            userRepository.save(user);
//            order.setCoupon(coupon);
//        }

        if (orderRequest.getCouponCode() != null) {
            // Initialize couponUsed set if it is null
            if (user.getCouponUsed() == null) {
                user.setCouponUsed(new HashSet<>());
            }

            // Check if the coupon is of type SINGLE and has already been used
            if (coupon.getCouponType() == CouponType.SINGLE && user.getCouponUsed().contains(coupon)) {
                throw new IllegalArgumentException("This coupon has already been used by the user.");
            }

            // Add the coupon to the user's couponUsed set
            user.getCouponUsed().add(coupon);
            userRepository.save(user);
            order.setCoupon(coupon);
        }
        return order;
    }


    public OrderResponse toResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        String userName = order.getUser().getUsername();
        Set<ProductResponseOrder> products = new HashSet<>();
        float totalPrice = order.getProducts().stream().mapToInt(Product::getPrice).sum();
        if(order.getCoupon() != null && order.getCoupon().getExpiryDate().isAfter(LocalDate.now()) ){
            Coupon coupon = order.getCoupon();
            float discount = (float) (100 - coupon.getDiscountPercent()) / 100;
            totalPrice = totalPrice * discount;
        }
        for (Product product : order.getProducts()) {
            ProductResponseOrder productResponseOrder = new ProductResponseOrder();
            productResponseOrder.setName(product.getName());
            productResponseOrder.setPrice(product.getPrice());
            products.add(productResponseOrder);

        }
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setProductInfo(products);
        orderResponse.setTotalPrice(totalPrice);
        orderResponse.setUserName(userName);

        return orderResponse;
    }
}
