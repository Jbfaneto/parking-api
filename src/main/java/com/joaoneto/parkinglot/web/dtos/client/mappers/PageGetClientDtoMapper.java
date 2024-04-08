package com.joaoneto.parkinglot.web.dtos.client.mappers;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.web.dtos.client.ClientGetResponseDto;
import com.joaoneto.parkinglot.web.dtos.client.PageGetClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageGetClientDtoMapper implements Function<Page<Client>, PageGetClientDto>{
    public static PageGetClientDtoMapper build(){
        return new PageGetClientDtoMapper();
    }




    @Override
    public PageGetClientDto apply(Page<Client> clients) {
        List<ClientGetResponseDto> clientGetResponseDto = clients.stream()
                .map(client -> ClientToClientGetResponseDtoMapper.build().apply(client))
                .collect(Collectors.toList());

        return new PageGetClientDto(new PageImpl<>(clientGetResponseDto, PageRequest.of(clients.getNumber(), clients.getSize()), clients.getTotalElements()));
    }
}
