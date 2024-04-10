package com.joaoneto.parkinglot.web.dtos.spot.Mapper;

import com.joaoneto.parkinglot.entities.Spot;
import com.joaoneto.parkinglot.web.dtos.spot.SpotGetDto;

import java.util.function.Function;

public class SpotToSpotGetDtoMapper implements Function<Spot, SpotGetDto> {
    public static SpotToSpotGetDtoMapper build() {
        return new SpotToSpotGetDtoMapper();
    }

    @Override
    public SpotGetDto apply(Spot spot) {
        String status = spot.getSpotStatus().getStringValue();
        return new SpotGetDto(spot.getId(), spot.getCode(), status);
    }
}
