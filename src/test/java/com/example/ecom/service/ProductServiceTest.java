package com.example.ecom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.ecom.dtos.product.ProductRequest;
import com.example.ecom.dtos.product.ProductResponse;
import com.example.ecom.dtos.product.ProductUpdateRequest;
import com.example.ecom.dtos.product.ProductUser;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.Role;
import com.example.ecom.entity.RoleEnum;
import com.example.ecom.entity.User;
import com.example.ecom.mapper.ProductMapper;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductMapper productMapper;


    private ProductService productService;

    private User user;
    private Product product;
    private Product product1;
    private ProductRequest productRequest;
    private ProductResponse productResponse;
    private UUID userId;
    private UUID productId;
    private Role role;
    Set<Role> roles = new HashSet<>();

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        productId = UUID.randomUUID();
        role = Role.builder()
                .id(1)
                .role(RoleEnum.ADMIN)
                .build();
        roles.add(role);
        user = User.builder()
                .id(userId)
                .username("Faizan")
                .email("faizan@example.com")
                .password("123")
                .role(roles)
                .build();
        product = Product.builder()
                .id(productId)
                .name("Lenovo Thinkpad T480")
                .price(60000)
                .quantity(50)
                .user(user)
                .build();
        product1 = Product.builder()
                .id(UUID.randomUUID())
                .name("Samsung S24")
                .price(200000)
                .quantity(10)
                .user(user)
                .build();
        productRequest = ProductRequest.builder()
                .name("Lenovo Thinkpad T480")
                .price(60000)
                .quantity(50)
                .userId(userId)
                .build();
        productResponse = ProductResponse.builder()
                .name("Lenovo Thinkpad T480")
                .price(60000)
                .quantity(50)
                .build();
        productService = new ProductService(productRepository, userRepository, orderRepository, productMapper);
    }

    @Test
    void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product1);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        List<?> allProducts = productService.getAll();

        assertNotNull(allProducts);
        assertEquals(productList.size(), allProducts.size());

    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals(productResponse, result);

    }

    @Test
    void testUpdateProduct() {
        ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                        .id(productId)
                        .price(20000)
                                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.updateProduct(productUpdateRequest);

        assertNotNull(result);
        assertEquals(productResponse.getName(), result.getName());
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));

        String result = productService.deleteProductById(productId);

        assertNotNull(result);
        assertEquals("Product Deleted", result);
    }

    @Test
    void testCreateProduct() {
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(productMapper.toEntity(productRequest, user)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.postProduct(productRequest);

        assertNotNull(result);
        assertEquals(productResponse.getName(), result.getName());
    }


    @Test
    void testFindProductByUserId() {
    List<Product> productList = new ArrayList<>();
    productList.add(product);
    productList.add(product1);

    ProductUser userProduct = ProductUser.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .build();

    ProductUser userProduct1 = ProductUser.builder()
            .id(product1.getId())
            .name(product1.getName())
            .price(product1.getPrice())
            .quantity(product1.getQuantity())
            .build();

    List<ProductUser> productUserList = List.of(userProduct, userProduct1);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(productRepository.findByUserId(userId)).thenReturn(productList);
    when(productMapper.toUserResponse(product)).thenReturn(userProduct);
    when(productMapper.toUserResponse(product1)).thenReturn(userProduct1);

    List<?> result = productService.findProductByUserId(userId);

    assertNotNull(result);
    assertEquals(productUserList.size(), result.size());
    assertEquals(productUserList, result);
}
}
