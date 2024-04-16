package com.joaoneto.parkinglot.web.dtos.parking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ParkingCreateDto(
        @NotBlank
        @Size(min = 8, max = 8)
        @Pattern(regexp = "^[A-Z]{3}-[0-9]{4}", message = "Invalid plate number format. Use AAA-0000.")
        String plateNumber,
        @NotBlank
        String brand,
        @NotBlank
        String model,
        @NotBlank
        String color,
        @NotBlank
        @Size(min = 11, max = 11)
        @CPF
        String clientCpf
) {
}
