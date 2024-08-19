package com.example.ecom.repository;

import com.example.ecom.entity.Role;
import com.example.ecom.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(RoleEnum role);
}
