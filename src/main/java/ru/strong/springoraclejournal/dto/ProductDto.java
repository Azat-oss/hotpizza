package ru.strong.springoraclejournal.dto;

import ru.strong.springoraclejournal.model.Category;

import java.math.BigDecimal;

public record ProductDto(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        Category category,
        String imageUrl,
        Boolean available
) {}
