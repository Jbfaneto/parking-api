package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.services.SpotService;
import com.joaoneto.parkinglot.web.dtos.spot.Mapper.SpotCreateDtoToSpotMapper;
import com.joaoneto.parkinglot.web.dtos.spot.Mapper.SpotToSpotCreateResponseDtoMapper;
import com.joaoneto.parkinglot.web.dtos.spot.Mapper.SpotToSpotGetDtoMapper;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateDto;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateResponseDto;
import com.joaoneto.parkinglot.web.dtos.spot.SpotGetDto;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/spots")
@RequiredArgsConstructor
public class SpotController {
    private final SpotService spotService;

    @Operation(summary = "Create a new spot", description = "Resource to create a new spot, " +
            "operation requires a bearer token to access it as an admin",
            security = @SecurityRequirement(name = "security"),
            tags = {"Spots"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Spot created with success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SpotCreateResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Spot code already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid code",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden access",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access")
            })

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpotCreateResponseDto> createSpot(@RequestBody @Valid SpotCreateDto spotDto) {
        var spot = spotService.createSpot(SpotCreateDtoToSpotMapper.build().apply(spotDto));
        var response = SpotToSpotCreateResponseDtoMapper.build().apply(spot);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(spot.getCode()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Get spot by code", description = "Resource to get a spot by its code, "
        + "operation requires a bearer token to access it as an admin",
        security = @SecurityRequirement(name = "security"),
        tags = {"Spots"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Spot found with success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SpotGetDto.class))),
            @ApiResponse(responseCode = "404", description = "Spot not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
        })

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpotGetDto> getByCode(@PathVariable String code) {
        var spot = spotService.findByCode(code);
        return ResponseEntity.ok(SpotToSpotGetDtoMapper.build().apply(spot));
    }
}
