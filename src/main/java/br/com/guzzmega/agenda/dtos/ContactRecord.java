package br.com.guzzmega.agenda.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactRecord(
        @NotBlank(message = "NickName: mandatory")
        String nickName,

        @NotBlank(message = "PhoneNumber: mandatory")
        String phoneNumber,

        @NotBlank(message = "Email: mandatory")
        @Email(message = "Email: mandatory")
        String email) {
}
