package com.joaoneto.parkinglot.web.dtos.spot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SpotCreateDto(
        @NotBlank
        @Size(min = 4, max = 4)
        String code,
        @NotBlank
        @Pattern(regexp = "FREE|OCCUPIED")
        String status
) {
}
