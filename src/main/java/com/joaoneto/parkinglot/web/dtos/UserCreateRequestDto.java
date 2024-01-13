package com.joaoneto.parkinglot.web.dtos;

import com.joaoneto.parkinglot.entities.enums.UserRole;

public record UserCreateRequestDto(String username, String password, UserRole role) {
}
