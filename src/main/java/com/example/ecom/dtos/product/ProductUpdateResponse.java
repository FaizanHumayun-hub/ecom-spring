package com.example.ecom.dtos.product;

import lombok.Data;

@Data
public class ProductUpdateResponse {
    private String name;
    private int price;
    private int quantity;
}
