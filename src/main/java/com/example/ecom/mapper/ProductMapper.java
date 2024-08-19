package com.example.ecom.mapper;

import com.example.ecom.dtos.product.ProductRequest;
import com.example.ecom.dtos.product.ProductResponse;
import com.example.ecom.dtos.product.ProductUpdateRequest;
import com.example.ecom.dtos.product.ProductUser;
import com.example.ecom.dtos.user.UserRequest;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequest productRequest, User user){
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setUser(user);
        return product;

    }

    public ProductResponse toResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());

        return productResponse;
    }


    public ProductRequest toRequest(Product product){
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(product.getName());
        productRequest.setPrice(product.getPrice());
        productRequest.setQuantity(product.getQuantity());
        return productRequest;

    }

    public ProductUpdateRequest toUpdateRequest(Product product){
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setName(product.getName());
        productUpdateRequest.setPrice(product.getPrice());
        productUpdateRequest.setQuantity(product.getQuantity());
        return productUpdateRequest;
    }

    public ProductUser toUserResponse(Product product){
        ProductUser productResponse = new ProductUser();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());

        return productResponse;
    }


}
