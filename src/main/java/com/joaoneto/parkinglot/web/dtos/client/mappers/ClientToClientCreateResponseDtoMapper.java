package com.joaoneto.parkinglot.web.dtos.client.mappers;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateResponseDto;

import java.util.function.Function;

public class ClientToClientCreateResponseDtoMapper implements Function<Client, ClientCreateResponseDto> {
    public static ClientToClientCreateResponseDtoMapper build() {
        return new ClientToClientCreateResponseDtoMapper();
    }

    @Override
    public ClientCreateResponseDto apply(Client client) {
        return new ClientCreateResponseDto(client.getId(), client.getName(), client.getCpf());
    }
}
