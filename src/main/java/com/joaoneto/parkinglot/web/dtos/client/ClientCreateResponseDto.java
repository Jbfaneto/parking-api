package com.joaoneto.parkinglot.web.dtos.client;

public record ClientCreateResponseDto(
        Long id,
        String name,
        String cpf
) {
}
