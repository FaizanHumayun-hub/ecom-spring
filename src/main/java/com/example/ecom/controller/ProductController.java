package com.example.ecom.controller;

import com.example.ecom.dtos.product.ProductRequest;
import com.example.ecom.dtos.product.ProductResponse;
import com.example.ecom.dtos.product.ProductUpdateRequest;
import com.example.ecom.entity.Product;
import com.example.ecom.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>( productService.getAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable UUID productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        return new ResponseEntity<>( productService.postProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductUpdateRequest product) {
            return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productId) {
            return new ResponseEntity<>(productService.deleteProductById(productId), HttpStatus.ACCEPTED);

    }

    @GetMapping("userproducts/{userId}")
    public ResponseEntity<?> userProducts(@PathVariable UUID userId) {
        return new ResponseEntity<>(productService.findProductByUserId(userId), HttpStatus.ACCEPTED);
    }


}