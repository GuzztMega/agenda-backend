package br.com.guzzmega.agenda.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record PersonRecord(
        @NotBlank(message = "Name: mandatory")
        String name,

        @NotBlank(message = "Document: mandatory")
        @Size(min=11, max=11, message = "Document: Invalid CPF")
        String document,

        @NotNull(message = "BirthDate: mandatory")
        LocalDate birthDate,

        @NotNull(message = "Contacts: must have at least one Contact")
        @NotEmpty(message = "Contacts: must have at least one Contact")
        List<ContactRecord> contacts) {
}
