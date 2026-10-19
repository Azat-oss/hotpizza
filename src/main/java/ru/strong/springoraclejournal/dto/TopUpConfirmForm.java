package ru.strong.springoraclejournal.dto;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

public record TopUpConfirmForm(
        @NotBlank(message = "Введите код")
        @Pattern(regexp = "^[0-9]{6}$", message = "Код состоит из 6 цифр")
        String code
) {}
