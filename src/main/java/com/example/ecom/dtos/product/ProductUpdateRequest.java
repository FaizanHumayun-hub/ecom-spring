package com.example.ecom.dtos.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequest {
    @NotNull(message = "product id cannot be null")
    private UUID id;
    @Max(value = 50, message = "Name cannot be greater than 50 characters long")
    private String name;
    private Integer price;
    @Range(max = 100000, message = "quantity must not be greater than 100,000")
    private Integer quantity;
}
