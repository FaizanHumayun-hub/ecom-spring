package com.example.ecom.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @Size(min = 5, max = 30, message = "username must be 5 to 30 characters long")
    private String username;
    @NotBlank(message = "Password is must")
    private String password;
}
