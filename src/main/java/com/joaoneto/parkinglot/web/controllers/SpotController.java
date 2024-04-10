package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.services.SpotService;
import com.joaoneto.parkinglot.web.dtos.spot.Mapper.SpotCreateDtoToSpotMapper;
import com.joaoneto.parkinglot.web.dtos.spot.Mapper.SpotToSpotCreateResponseDtoMapper;
import com.joaoneto.parkinglot.web.dtos.spot.Mapper.SpotToSpotGetDtoMapper;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateDto;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateResponseDto;
import com.joaoneto.parkinglot.web.dtos.spot.SpotGetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/spots")
@RequiredArgsConstructor
public class SpotController {
    private SpotService spotService;

    @PostMapping
    public ResponseEntity<SpotCreateResponseDto> createSpot(@RequestBody @Valid SpotCreateDto spotDto) {
        var spot = spotService.createSpot(SpotCreateDtoToSpotMapper.build().apply(spotDto));
        var response = SpotToSpotCreateResponseDtoMapper.build().apply(spot);
        URI uri = URI.create("/spots/" + response.id());

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<SpotGetDto> getByCode(@PathVariable String code) {
        var spot = spotService.findByCode(code);
        return ResponseEntity.ok(SpotToSpotGetDtoMapper.build().apply(spot));
    }
}
