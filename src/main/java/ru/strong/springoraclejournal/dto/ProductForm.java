package ru.strong.springoraclejournal.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.strong.springoraclejournal.model.Category;

import java.math.BigDecimal;

public record ProductForm(
        @Nullable Integer id,

        @NotBlank(message = "Название обязательно")
        @Size(min = 2, max = 200, message = "Название 2-200 символов")
        String name,

        @NotBlank(message = "Описание обязательно")
        @Size(max = 2000, message = "Описание слишком длинное")
        String description,

        @NotNull(message = "Цена обязательна")
        @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
        BigDecimal price,

        @NotNull(message = "Категория обязательна")
        Category category,

        @Nullable
        @Size(max = 500, message = "URL изображения слишком длинный")
        String imageUrl,

        @Nullable
        Boolean available
) {}
