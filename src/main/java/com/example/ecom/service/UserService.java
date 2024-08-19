package com.example.ecom.service;

import com.example.ecom.customException.NotFoundException;
import com.example.ecom.dtos.product.ProductResponse;
import com.example.ecom.dtos.product.ProductUser;
import com.example.ecom.dtos.user.UpdateUserRequest;
import com.example.ecom.dtos.user.UserIdRequest;
import com.example.ecom.dtos.user.UserRequest;
import com.example.ecom.dtos.user.UserResponse;
import com.example.ecom.entity.Order;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.User;
import com.example.ecom.mapper.OrderMapper;
import com.example.ecom.mapper.ProductMapper;
import com.example.ecom.mapper.UserMapper;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public List<?> getAllUser() {
        return (userRepository.findAll().stream()
                .map(userMapper::toResponse).toList());
    }

    public UserResponse addUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toResponse(userRepository.save(user));

    }

    public User getFullUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    public UserResponse getUserById(UUID userId) {
        return userMapper.toResponse(userRepository.findById(userId).orElseThrow(() -> new NotFoundException('U')));
    }

    public String deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException('U'));
        List<Product> products = productRepository.findByUserId(userId);
        products.forEach(p -> orderRepository.deleteOrderProductsByProductId(p.getId()));
        List<Order> orders = orderRepository.findByUserId(userId);
        orderRepository.deleteAll(orders);
        productRepository.deleteAll(products);
        userRepository.delete(user);
        return "User Deleted";
    }

    public UserResponse updateUser(UpdateUserRequest user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException('U'));

        return userMapper.toUpdateRequest(existingUser, user);
    }


}
