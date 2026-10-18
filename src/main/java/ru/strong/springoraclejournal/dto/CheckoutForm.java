package ru.strong.springoraclejournal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckoutForm(
        @NotBlank(message = "Адрес доставки обязателен")
        @Size(max = 500, message = "Адрес слишком длинный")
        String shippingAddress,

        @NotBlank(message = "Телефон обязателен")
        @Size(min = 6, max = 30, message = "Телефон 6-30 символов")
        String phone
) {}
