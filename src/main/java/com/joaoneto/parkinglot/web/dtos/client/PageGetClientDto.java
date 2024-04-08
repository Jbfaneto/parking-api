package com.joaoneto.parkinglot.web.dtos.client;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public record PageGetClientDto(
        Page<ClientGetResponseDto> clients
) {}
