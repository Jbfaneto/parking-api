package com.joaoneto.parkinglot.web.dtos.spot.mapper;

import com.joaoneto.parkinglot.entities.Spot;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateResponseDto;

import java.util.function.Function;

public class SpotToSpotCreateResponseDtoMapper implements Function<Spot, SpotCreateResponseDto> {
    public static SpotToSpotCreateResponseDtoMapper build(){
        return new SpotToSpotCreateResponseDtoMapper();
    }

    @Override
    public SpotCreateResponseDto apply(Spot spot) {
        String status = spot.getSpotStatus().getStringValue();
        return new SpotCreateResponseDto(spot.getId(), spot.getCode(), status);
    }
}
