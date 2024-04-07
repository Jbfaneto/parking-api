package com.joaoneto.parkinglot.web.dtos.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClientCreateRequestDto(
        @NotBlank
        @Size(min = 4, max = 100)
        String name,
        @NotBlank
        @Size(min = 11, max = 11)
        @CPF
        String cpf
        ) {
}
