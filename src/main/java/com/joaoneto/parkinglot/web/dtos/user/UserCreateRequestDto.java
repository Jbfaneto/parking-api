package com.joaoneto.parkinglot.web.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequestDto(@Email(message = "Email must be valid")
                                   @NotBlank(message = "username can't be blank")
                                   String username,
                                   @NotBlank(message = "password can't be blank")
                                   @Size(min = 6, message = "Password must be at least 6 characters long")
                                   String password) {
}
