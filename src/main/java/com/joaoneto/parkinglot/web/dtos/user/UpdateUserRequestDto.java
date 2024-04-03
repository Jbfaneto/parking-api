package com.joaoneto.parkinglot.web.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(@NotBlank (message = "currentPassword can't be blank")
                                   @Size(min = 6, message = "Password must be at least 6 characters long")
                                   String currentPassword,
                                   @NotBlank (message = "newPassword can't be blank")
                                   @Size(min = 6, message = "Password must be at least 6 characters long")
                                   String newPassword,
                                   @NotBlank (message = "passwordConfirmation can't be blank")
                                   @Size(min = 6, message = "Password must be at least 6 characters long")
                                   String passwordConfirmation) {
}