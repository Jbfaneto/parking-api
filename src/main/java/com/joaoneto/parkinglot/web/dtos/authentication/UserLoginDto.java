package com.joaoneto.parkinglot.web.dtos.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDto(
    @NotBlank
    @Email(message = "Invalid email", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    String username,
    @NotBlank(message = "password can't be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password
) {
}
