package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.entities.ClientSpot;
import com.joaoneto.parkinglot.services.ClientService;
import com.joaoneto.parkinglot.services.ClientSpotService;
import com.joaoneto.parkinglot.services.ParkingService;
import com.joaoneto.parkinglot.web.dtos.parking.ParkingCreateDto;
import com.joaoneto.parkinglot.web.dtos.parking.ParkingResponseDto;
import com.joaoneto.parkinglot.web.dtos.parking.mapper.ClientSpotToParkingResponseDtoMapper;
import com.joaoneto.parkinglot.web.dtos.parking.mapper.ParkingCreateDtoToClientSpotMapper;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {
    private final ParkingService parkingService;
    private final ClientService clientService;
    private final ClientSpotService clientSpotService;

    @Operation(summary = "Check in a car", description = "Resource to check a car in" +
    "The operation requires a bearer token to access it as an admin.",
        security = @SecurityRequirement(name = "security"),
        tags = {"Parking"},
        responses = {
            @ApiResponse(responseCode = "201", description = "Car checked in with success",
                    headers = @Header(name = "Location", description = "URI with the receipt of the check in"),
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Spot already occupied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "400", description = "Invalid spot",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "404", description = "Client or free spot not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PostMapping("/checkin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkIn(@RequestBody @Valid ParkingCreateDto parkingCreateDto) {
        ClientSpot clientSpot = ParkingCreateDtoToClientSpotMapper.build().apply(parkingCreateDto);
        clientSpot.setClient(clientService.findByCpf(parkingCreateDto.clientCpf()));
        parkingService.checkIn(clientSpot);
        ParkingResponseDto response = ClientSpotToParkingResponseDtoMapper.build().apply(clientSpot);
        URI uri = URI.create("/api/v1/parking/checkin/" + response.receipt());
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/checkin/{receipt}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ParkingResponseDto> getReceipt(@PathVariable String receipt) {
        ClientSpot clientSpot = clientSpotService.findByReceiptAndExitTimeIsNull(receipt);
        ParkingResponseDto response = ClientSpotToParkingResponseDtoMapper.build().apply(clientSpot);
        return ResponseEntity.ok(response);
    }

}
