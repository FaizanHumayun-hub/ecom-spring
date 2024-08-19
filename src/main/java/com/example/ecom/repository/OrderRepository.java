package com.example.ecom.repository;

import com.example.ecom.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Modifying
    @Query("DELETE FROM Order o WHERE o.orderId = :orderId")
    void deleteOrderProductsByOrderId(@Param("orderId") UUID orderId);

    @Modifying
    @Query(value = "DELETE FROM orders_products WHERE product_id = :productId", nativeQuery = true)
    void deleteOrderProductsByProductId(@Param("productId") UUID productId);

    List<Order> findByUserId(UUID userId);

}
