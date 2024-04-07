package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.jwt.JwtUserDetails;
import com.joaoneto.parkinglot.services.ClientService;
import com.joaoneto.parkinglot.services.UserService;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateRequestDto;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateResponseDto;
import com.joaoneto.parkinglot.web.dtos.client.mappers.ClientCreateRequestDtoToClientMapper;
import com.joaoneto.parkinglot.web.dtos.client.mappers.ClientToClientCreateResponseDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientCreateResponseDto> createClient(@RequestBody @Valid ClientCreateRequestDto client,
                                                                @AuthenticationPrincipal JwtUserDetails userDetails){

        Client newClient = ClientCreateRequestDtoToClientMapper.build().apply(client);
        newClient.setUser(userService.getUserById(userDetails.getId()));
        clientService.createClient(newClient);
        ClientCreateResponseDto response = ClientToClientCreateResponseDtoMapper.build().apply(newClient);
        URI uri = URI.create(String.format("/api/v1/clients/%s", response.id()));

        return ResponseEntity.created(uri).body(response);
    }

}
