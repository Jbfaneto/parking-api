package com.joaoneto.parkinglot.web.dtos;

public record UpdateUserRequestDto(String currentPassword, String newPassword, String passwordConfirmation) {
}
