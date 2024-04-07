package com.joaoneto.parkinglot.web.dtos.client.mappers;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateRequestDto;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateResponseDto;

import java.util.function.Function;

public class ClientCreateRequestDtoToClientMapper implements Function<ClientCreateRequestDto, Client> {
    public static ClientCreateRequestDtoToClientMapper build() {
        return new ClientCreateRequestDtoToClientMapper();
    }
    @Override
    public Client apply(ClientCreateRequestDto clientCreateRequestDto) {
        return new Client(clientCreateRequestDto.name(), clientCreateRequestDto.cpf());
    }
}
