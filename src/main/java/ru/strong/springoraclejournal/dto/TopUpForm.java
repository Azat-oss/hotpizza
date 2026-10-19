package ru.strong.springoraclejournal.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record TopUpForm(
        @NotBlank(message = "Номер карты обязателен")
        @Pattern(regexp = "^[0-9]{13,19}$", message = "Номер карты должен содержать 13-19 цифр")
        String cardNumber,

        @NotNull(message = "Сумма обязательна")
        @DecimalMin(value = "1.00", message = "Минимальная сумма 1 ₽")
        @DecimalMax(value = "100000.00", message = "Максимальная сумма 100 000 ₽")
        BigDecimal amount
) {}
