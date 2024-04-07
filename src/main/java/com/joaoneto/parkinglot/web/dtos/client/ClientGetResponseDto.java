package com.joaoneto.parkinglot.web.dtos.client;

import com.joaoneto.parkinglot.entities.User;

public record ClientGetResponseDto(
        long id,
        String name,
        String cpf,
        User user
) {
}
