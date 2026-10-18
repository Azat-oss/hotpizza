package ru.strong.springoraclejournal.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserForm(
        @Nullable Integer id,

        @NotBlank(message = "Имя обязательно")
        @Size(min = 2, max = 50, message = "Имя должно быть 2-50 символов")
        String name,

        @Email(message = "Некорректный email")
        @NotBlank(message = "Email обязателен")
        String email,

        @Nullable
        @Size(min = 6, max = 100, message = "Пароль должен быть 6-100 символов")
        String password,

        @NotBlank(message = "Телефон обязателен")
        @Pattern(regexp = "^[+]?[0-9\\-\\s()]{7,20}$", message = "Некорректный телефон")
        String phone,

        @Nullable
        @Size(max = 300, message = "Адрес слишком длинный")
        String address
) {}
