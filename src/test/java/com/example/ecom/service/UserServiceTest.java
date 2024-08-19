package com.example.ecom.service;

import com.example.ecom.dtos.user.UpdateUserRequest;
import com.example.ecom.dtos.user.UserRequest;
import com.example.ecom.dtos.user.UserResponse;
import com.example.ecom.entity.*;
import com.example.ecom.mapper.UserMapper;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;
    private UUID userId;

    private Product product;
    private Order order;
    private Role role;
    Set<Role> roles = new HashSet<>();

    @BeforeEach
    void setup() {
        Set<Product> products = new HashSet<>();
        userId = UUID.randomUUID();
        //Set<Role> roles = new HashSet<>();
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

        userRequest = UserRequest.builder()
                .username("Faizan")
                .email("faizan@example.com")
                .password("123")
                .role(Collections.singleton("ADMIN"))
                .build();

        userResponse = UserResponse.builder()
                .username("Faizan")
                .email("faizan@example.com")
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
                .orderId(UUID.randomUUID())
                .user(user)
                .products(products)
                .build();




        userService = new UserService(userRepository, productRepository, orderRepository ,passwordEncoder, userMapper);
    }


    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userResponse.getUsername(), result.getUsername());
        assertEquals(userResponse.getEmail(), result.getEmail());
    }

    @Test
    void testAddUser_Success() {
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.addUser(userRequest);

        assertNotNull(result);
        assertEquals(userResponse, result);
        assertEquals(userResponse.getUsername(), result.getUsername());
        assertEquals(userResponse.getEmail(), result.getEmail());
    }

    @Test
    void testDeleteUser() {
        List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        products.add(product);
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(productRepository.findByUserId(userId)).thenReturn(products);
        when(orderRepository.findByUserId(userId)).thenReturn(orders);

        String result = userService.deleteUser(userId);

        assertEquals("User Deleted", result);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        User user2 = User.builder()
                .id(userId)
                .username("Bial")
                .email("bilal@example.com")
                .password("123")
                .role(roles)
                .build();
        users.add(user);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        List<?> userList = userService.getAllUser();

        assertNotNull(userList);
        assertEquals(2, userList.size());

    }

    @Test
    void testUpdateUser_Success() {
        UpdateUserRequest userUpdateRequest = UpdateUserRequest.builder()
                .id(userId)
                .username("Faizan")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(userMapper.toUpdateRequest(user, userUpdateRequest)).thenReturn(userResponse);

        UserResponse result = userService.updateUser(userUpdateRequest);

        assertNotNull(result);
        assertEquals(userResponse.getUsername(), result.getUsername());

    }
}
