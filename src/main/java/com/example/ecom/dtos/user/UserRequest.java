package com.example.ecom.dtos.user;

import com.example.ecom.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @Size(min = 5, max = 30, message = "username must be 5 to 30 characters long")
    private String username;
    @Email
    private String email;
    @NotBlank(message = "Password is must")
    private String password;
    @NotEmpty(message = "Role cannot be empty")
    private Set<String> role;
}
