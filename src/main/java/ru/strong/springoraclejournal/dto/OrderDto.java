package ru.strong.springoraclejournal.dto;

import ru.strong.springoraclejournal.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record OrderDto(
        Integer id,
        LocalDateTime createdAt,
        BigDecimal totalPrice,
        OrderStatus status,
        String shippingAddress,
        String phone,
        List<OrderItemDto> items
) {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public String getCreatedAtFormatted() {
        return createdAt == null ? null : createdAt.format(FORMATTER);
    }
}
