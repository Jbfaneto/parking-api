package com.joaoneto.parkinglot.web.dtos.parking.mapper;

import com.joaoneto.parkinglot.entities.ClientSpot;
import com.joaoneto.parkinglot.web.dtos.parking.ParkingResponseDto;

import java.util.function.Function;

public class ClientSpotToParkingResponseDtoMapper implements Function<ClientSpot, ParkingResponseDto> {
    public static ClientSpotToParkingResponseDtoMapper build() {
        return new ClientSpotToParkingResponseDtoMapper();
    }

    @Override
    public ParkingResponseDto apply(ClientSpot clientSpot) {
        return new ParkingResponseDto(
                clientSpot.getPlateNumber(),
                clientSpot.getBrand(),
                clientSpot.getModel(),
                clientSpot.getColor(),
                clientSpot.getClient().getCpf(),
                clientSpot.getReceipt(),
                clientSpot.getEntryTime(),
                clientSpot.getExitTime(),
                clientSpot.getSpot().getCode(),
                clientSpot.getPrice(),
                clientSpot.getDiscount()
        );
    }
}
