package com.example.ecom.dtos.user;

import com.example.ecom.entity.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @NotNull(message = "User id cannot be null")
    private UUID id;
    @Size(max = 30, message = "username must be 5 to 30 characters long")
    private String username;
    private Set<Role> role;
}
