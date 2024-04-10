package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.services.SpotService;
import com.joaoneto.parkinglot.web.dtos.spot.Mapper.SpotCreateDtoToSpotMapper;
import com.joaoneto.parkinglot.web.dtos.spot.Mapper.SpotToSpotCreateResponseDtoMapper;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateDto;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/spots")
@RequiredArgsConstructor
public class SpotController {
    private SpotService spotService;

    @PostMapping
    public ResponseEntity<SpotCreateResponseDto> createSpot(SpotCreateDto spotDto) {
        var spot = spotService.createSpot(SpotCreateDtoToSpotMapper.build().apply(spotDto));
        var response = SpotToSpotCreateResponseDtoMapper.build().apply(spot);
        URI uri = URI.create("/spots/" + response.id());

        return ResponseEntity.created(uri).body(response);
    }
}
