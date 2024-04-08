package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.jwt.JwtUserDetails;
import com.joaoneto.parkinglot.services.ClientService;
import com.joaoneto.parkinglot.services.UserService;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateRequestDto;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateResponseDto;
import com.joaoneto.parkinglot.web.dtos.client.ClientGetResponseDto;
import com.joaoneto.parkinglot.web.dtos.client.PageGetClientDto;
import com.joaoneto.parkinglot.web.dtos.client.mappers.ClientCreateRequestDtoToClientMapper;
import com.joaoneto.parkinglot.web.dtos.client.mappers.ClientToClientCreateResponseDtoMapper;
import com.joaoneto.parkinglot.web.dtos.client.mappers.ClientToClientGetResponseDtoMapper;
import com.joaoneto.parkinglot.web.dtos.client.mappers.PageGetClientDtoMapper;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Clients", description = "Client controller for creating and getting clients")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @Operation(summary = "Create a new client", description = "Resource to create a new client bound to a user. " +
    "Operation requires a bearer token to access it",
    security = @SecurityRequirement(name = "security"),
    tags = {"Clients"},
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
                                                                @AuthenticationPrincipal JwtUserDetails userDetails) {

        Client newClient = ClientCreateRequestDtoToClientMapper.build().apply(client);
        newClient.setUser(userService.getUserById(userDetails.getId()));
        clientService.createClient(newClient);
        ClientCreateResponseDto response = ClientToClientCreateResponseDtoMapper.build().apply(newClient);
        URI uri = URI.create(String.format("/api/v1/clients/%s", response.id()));

        return ResponseEntity.created(uri).body(response);
    }


    @Operation(summary = "Get a client by id", description = "Resource to get a client by id. " +
    "Operation requires a bearer token to access it with ADMIN Role",
            security = @SecurityRequirement(name = "security"),
            tags = {"Clients"},
    responses = {
            @ApiResponse(responseCode = "200", description = "Client Found with success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientGetResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientGetResponseDto> getClient(@PathVariable Long id) {
        Client client = clientService.findClientById(id);
        ClientGetResponseDto response = ClientToClientGetResponseDtoMapper.build().apply(client);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all clients", description = "Resource to get all clients. " +
            "Operation requires a bearer token to access it with ADMIN Role",
            security = @SecurityRequirement(name = "security"),
            tags = {"Clients"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clients Found with success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageGetClientDtoMapper.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden access",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access")
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageGetClientDto> getAllClients(Pageable pageable){
        Page<Client> clients = clientService.findAllClients(pageable);
        PageGetClientDto response = PageGetClientDtoMapper.build().apply(clients);
        return ResponseEntity.ok(response);
    }

}
