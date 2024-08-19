package com.example.ecom.service;

import com.example.ecom.customException.NotFoundException;
import com.example.ecom.dtos.product.ProductRequest;
import com.example.ecom.dtos.product.ProductResponse;
import com.example.ecom.dtos.product.ProductUpdateRequest;
import com.example.ecom.dtos.product.ProductUser;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.User;
import com.example.ecom.mapper.ProductMapper;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductMapper productMapper;

    public List<?> getAll() {
        return  (productRepository.findAll().stream()
                .map(productMapper::toResponse).toList());
    }

    public ProductResponse getProductById(UUID id) {
        return productMapper.toResponse(productRepository.findById(id).orElseThrow(()-> new NotFoundException('P')));
    }

    public Product getFullProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(()-> new NotFoundException('P'));
    }

    public ProductResponse postProduct(ProductRequest product) {
        User user = userRepository.findById(product.getUserId()).orElseThrow(()-> new NotFoundException('U'));
        Product newProduct = productMapper.toEntity(product, user);
        return productMapper.toResponse(productRepository.save(newProduct));
    }


    public ProductResponse updateProduct(ProductUpdateRequest product) {
        Product existingProduct = productRepository.findById(product.getId()).orElseThrow(()-> new NotFoundException('P'));

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getQuantity() != null) {
            existingProduct.setQuantity(product.getQuantity());
        }


        return productMapper.toResponse(productRepository.save(existingProduct));
    }

    public String deleteProductById(UUID id) {
            productRepository.findById(id).orElseThrow(()-> new NotFoundException('P'));
            orderRepository.deleteOrderProductsByProductId(id);
            productRepository.deleteById(id);
            return "Product Deleted";

    }

    public List<?> findProductByUserId(UUID userId) {
        userRepository.findById(userId).orElseThrow(()-> new NotFoundException('U'));
        if (productRepository.findByUserId(userId).isEmpty()) {
            throw new NotFoundException('Z');
        }
        List<Product> product = productRepository.findByUserId(userId);
        return product.stream()
                .map(productMapper::toUserResponse).toList();
    }


}
