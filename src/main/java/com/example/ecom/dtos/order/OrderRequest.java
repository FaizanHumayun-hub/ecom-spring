package com.example.ecom.dtos.order;

import com.example.ecom.entity.Product;
import com.example.ecom.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotNull(message = "Product id cannot be null")
    @JsonProperty("product_id")
    private Set<UUID> productId;

    @NotNull(message = "User cannot be null")
    @JsonProperty("user_id")
    private UUID userId;

    private String couponCode;
}
