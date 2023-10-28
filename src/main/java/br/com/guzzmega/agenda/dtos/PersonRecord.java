package br.com.guzzmega.agenda.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonRecord(
        @NotBlank String name,
        @NotBlank String document,
        @NotBlank String phoneNumber,
        @NotBlank String email,
        @NotNull LocalDate birthDate) {
}
