package com.joaoneto.parkinglot.web.dtos.client.mappers;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.web.dtos.client.ClientGetResponseDto;

import java.util.function.Function;

public class ClientToClientGetResponseDtoMapper implements Function<Client, ClientGetResponseDto> {
    public static ClientToClientGetResponseDtoMapper build() {
        return new ClientToClientGetResponseDtoMapper();
    }

    @Override
    public ClientGetResponseDto apply(Client client) {
        return new ClientGetResponseDto(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getUser().getUsername()
        );
    }
}
