package com.example.ecom.dtos.role;

import lombok.Data;

import java.util.Set;

@Data
public class RoleResponse {
    private Set<String> roles;
}
