package com.example.ecom.service;

import com.example.ecom.dtos.role.RoleDto;
import com.example.ecom.entity.Role;
import com.example.ecom.entity.RoleEnum;
import com.example.ecom.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createRole(RoleDto role) {
        Role roleEntity = new Role();
        roleEntity.setRole(RoleEnum.valueOf(role.getRoleName()));
        return roleRepository.save(roleEntity);
    }
}
