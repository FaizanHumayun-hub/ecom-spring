package com.example.ecom.dtos.product;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    @Size(min = 5, max = 50, message = "name must be between 5 and 50 characters long")
    private String name;

    @NotNull
    @Range(min = 1, max = 999999999, message = "Price cannot be negative or greater than 999999999")
    private Integer price;

    @NotNull(message = "quantity cannot be null")
    @Range(min = 0, max = 100000, message = "quantity must not be greater than 100,000 or less than 0")
    private Integer quantity;

    @NotNull
    @JsonProperty("user_id")
    private UUID userId;
}
