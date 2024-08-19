package com.example.ecom.controller;

import com.example.ecom.dtos.order.OrderRequest;
import com.example.ecom.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/allorders")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> findOrderById(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.findOrderById(orderId), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrderById(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.deleteOrderById(orderId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/order-of-user/{userId}")
    public ResponseEntity<?> getOrderByUserId(@PathVariable UUID userId) {
        return new ResponseEntity<>(orderService.getOrderByUserId(userId), HttpStatus.OK);
    }
}
