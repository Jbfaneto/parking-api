package com.joaoneto.parkinglot.web.dtos.client.mappers;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.web.dtos.client.ClientGetResponseDto;

import java.util.List;
import java.util.function.Function;

public class ListGetClientDtoMapper implements Function<List<Client>, List<ClientGetResponseDto>> {
    public static ListGetClientDtoMapper build() {
        return new ListGetClientDtoMapper();
    }


    @Override
    public List<ClientGetResponseDto> apply(List<Client> clients) {
        return clients.stream()
                .map(client -> ClientToClientGetResponseDtoMapper.build().apply(client))
                .collect(java.util.stream.Collectors.toList());
    }
}
