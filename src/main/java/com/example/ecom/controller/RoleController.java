package com.example.ecom.controller;

import com.example.ecom.dtos.role.RoleDto;
import com.example.ecom.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody RoleDto role) {
        return new ResponseEntity<>(roleService.createRole(role) , HttpStatus.CREATED);
    }
}
