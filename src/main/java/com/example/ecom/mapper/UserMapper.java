package com.example.ecom.mapper;

import com.example.ecom.dtos.product.ProductResponse;
import com.example.ecom.dtos.user.UpdateUserRequest;
import com.example.ecom.dtos.user.UserRequest;
import com.example.ecom.dtos.user.UserResponse;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.Role;
import com.example.ecom.entity.RoleEnum;
import com.example.ecom.entity.User;
import com.example.ecom.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class UserMapper {
    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User toEntity(UserRequest userRequest) {
        Set<Role> roles = new HashSet<>();
        for (String role: userRequest.getRole()){
            roles.add(roleRepository.findByRole(RoleEnum.valueOf(role)));
        }
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(roles);
        return user;
    }

//    public UserRequest toRequest(User user) {
//        UserRequest userRequest = new UserRequest();
//        userRequest.setUsername(user.getUsername());
//        userRequest.setEmail(user.getEmail());
//        userRequest.setPassword(user.getPassword());
//        userRequest.setRole(user.getRole());
//        return userRequest;
//
//    }

    public UserResponse toResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        return userResponse;
    }

    public UserResponse toUpdateRequest(User existingUser, UpdateUserRequest user) {
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        return toResponse(existingUser);
    }

}
