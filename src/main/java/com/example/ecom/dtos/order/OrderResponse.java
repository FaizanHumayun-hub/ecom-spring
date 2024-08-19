package com.example.ecom.dtos.order;

import com.example.ecom.dtos.product.ProductResponseOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("total_price")
    private float totalPrice;
    @JsonProperty("product_info")
    private Set<ProductResponseOrder> productInfo;
}
