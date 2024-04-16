package com.joaoneto.parkinglot.web.dtos.parking.mapper;

import com.joaoneto.parkinglot.entities.ClientSpot;
import com.joaoneto.parkinglot.web.dtos.parking.ParkingCreateDto;

import java.util.function.Function;

public class ParkingCreateDtoToClientSpotMapper implements Function<ParkingCreateDto, ClientSpot>{
    public static ParkingCreateDtoToClientSpotMapper build() {
        return new ParkingCreateDtoToClientSpotMapper();
    }

    @Override
    public ClientSpot apply(ParkingCreateDto parkingCreateDto) {
        return new ClientSpot(
                parkingCreateDto.plateNumber(),
                parkingCreateDto.brand(),
                parkingCreateDto.model(),
                parkingCreateDto.color()
        );
    }
}
