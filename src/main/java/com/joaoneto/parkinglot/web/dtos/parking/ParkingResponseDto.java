package com.joaoneto.parkinglot.web.dtos.parking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ParkingResponseDto(
        String plateNumber,
        String brand,
        String model,
        String color,
        String clientCpf,
        String receipt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime entryTime,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime exitTime,
        String spotCode,
        BigDecimal price,
        BigDecimal discount) {
}
