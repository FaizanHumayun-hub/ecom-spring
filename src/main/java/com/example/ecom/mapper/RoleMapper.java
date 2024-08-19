package com.example.ecom.mapper;

import com.example.ecom.dtos.role.RoleDto;
import com.example.ecom.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName(String.valueOf(role.getRole()));
        return roleDto;
    }
}
