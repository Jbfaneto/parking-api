package com.joaoneto.parkinglot.web.dtos.spot;

public record SpotCreateResponseDto(
        Long id,
        String code,
        String status
) {
}
