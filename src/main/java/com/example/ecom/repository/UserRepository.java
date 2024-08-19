package com.example.ecom.repository;

import com.example.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<Object> findByUsername(String username);
    Optional<Object> findByEmail(String email);
}
