package ru.strong.springoraclejournal.dto;

import java.math.BigDecimal;

public record CartItemDto(
        Integer cartItemId,
        ProductDto product,
        Integer quantity,
        BigDecimal lineTotal
) {}
