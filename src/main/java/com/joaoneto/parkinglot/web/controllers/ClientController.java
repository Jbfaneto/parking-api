package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.jwt.JwtUserDetails;
import com.joaoneto.parkinglot.services.ClientService;
import com.joaoneto.parkinglot.services.UserService;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateRequestDto;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateResponseDto;
import com.joaoneto.parkinglot.web.dtos.client.mappers.ClientCreateRequestDtoToClientMapper;
import com.joaoneto.parkinglot.web.dtos.client.mappers.ClientToClientCreateResponseDtoMapper;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clients", description = "Client controller for creating and getting clients")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @Operation(summary = "Create a new client", description = "Resource to create a new client binded to a user. " +
    "Operation requires a bearer token to access it",
    responses = {
            @ApiResponse(responseCode = "201", description = "Client created with success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientCreateResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Client CPF already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "400", description = "Invalid CPF or name",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class)))
    })

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
