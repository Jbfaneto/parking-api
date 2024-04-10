package com.joaoneto.parkinglot.web.dtos.spot.Mapper;

import com.joaoneto.parkinglot.entities.Spot;
import com.joaoneto.parkinglot.entities.enums.SpotStatus;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateDto;

import java.util.function.Function;

public class SpotCreateDtoToSpotMapper implements Function<SpotCreateDto, Spot>{
    public static SpotCreateDtoToSpotMapper build(){
        return new SpotCreateDtoToSpotMapper();
    }

    @Override
    public Spot apply(SpotCreateDto dto) {
        SpotStatus status = SpotStatus.fromStringValue(dto.spotStatus());
        return new Spot(dto.code(), status);
    }
}
