package ru.strong.springoraclejournal.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        String productName,
        String imageUrl,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal lineTotal
) {}
