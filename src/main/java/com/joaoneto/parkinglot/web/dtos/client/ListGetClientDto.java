package com.joaoneto.parkinglot.web.dtos.client;

import java.util.List;

public record ListGetClientDto(
        List<ClientGetResponseDto> clients
) {
}
