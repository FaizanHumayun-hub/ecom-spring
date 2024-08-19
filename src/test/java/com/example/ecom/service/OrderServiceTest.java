package com.example.ecom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.ecom.dtos.order.OrderRequest;
import com.example.ecom.dtos.order.OrderResponse;
import com.example.ecom.dtos.product.ProductResponseOrder;
import com.example.ecom.entity.Order;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.Role;
import com.example.ecom.entity.User;
import com.example.ecom.mapper.OrderMapper;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private UserRepository userRepository;

    private OrderService orderService;

    private Order order;
    private UUID orderId;
    private UUID productId;
    private UUID userId;
    private OrderRequest orderRequest;
    private OrderResponse orderResponse;
    private Product product;
    private User user;
    private ProductResponseOrder productResponseOrder;
    private Role role;
    Set<Role> roles = new HashSet<>();


    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        userId = UUID.randomUUID();
        productId = UUID.randomUUID();
        Set<UUID> productIds = new HashSet<>();
        productIds.add(productId);
        Set<Product> products = new HashSet<>();
        Set<ProductResponseOrder> productResponseOrders = new HashSet<>();
        productResponseOrder = ProductResponseOrder.builder().price(60000).name("Lenovo Thinkpad T480").build();
        productResponseOrders.add(productResponseOrder);
        LocalDateTime time = LocalDateTime.now();
        user = User.builder()
                .id(userId)
                .username("Faizan")
                .email("faizan@example.com")
                .password("123")
                .role(roles)
                .build();
        product = Product.builder()
                .id(UUID.randomUUID())
                .name("Lenovo Thinkpad T480")
                .price(60000)
                .quantity(50)
                .user(user)
                .build();
        products.add(product);

        order = Order.builder()
                .orderId(orderId)
                .user(user)
                .products(products)
                .build();
        orderResponse = OrderResponse.builder()
                .userName(user.getUsername())
                .productInfo(productResponseOrders)
                .totalPrice(60000)
                .createdAt(time)
                .build();

        orderRequest = OrderRequest.builder()
                .userId(user.getId())
                .productId(productIds)
                .build();



        orderService = new OrderService(orderRepository, orderMapper, userRepository);
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toEntity(orderRequest)).thenReturn(order);
        when(orderMapper.toResponse(order)).thenReturn(orderResponse);

        OrderResponse result = orderService.createOrder(orderRequest);
        assertNotNull(result);
        assertEquals(orderResponse, result);
    }

    @Test
    void testFindOrderById() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toResponse(order)).thenReturn(orderResponse);

        OrderResponse result = orderService.findOrderById(orderId);
        assertNotNull(result);
        assertEquals(orderResponse, result);
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        when(orderMapper.toResponse(order)).thenReturn(orderResponse);

        List<OrderResponse> result = orderService.getAllOrders();
        assertNotNull(result);
        assertEquals(Arrays.asList(orderResponse), result);
    }

    @Test
    void testDeleteOrder() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        String result = orderService.deleteOrderById(orderId);

        assertNotNull(result);
        assertEquals("Order Deleted", result);
    }

    @Test
    void testGetOrderByUserId() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(orderRepository.findByUserId(userId)).thenReturn(Arrays.asList(order));
        when(orderMapper.toResponse(order)).thenReturn(orderResponse);

        List<?> result = orderService.getOrderByUserId(userId);
        assertNotNull(result);
        assertEquals(Arrays.asList(orderResponse), result);
    }

}
