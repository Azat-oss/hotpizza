package ru.strong.springoraclejournal.dto;

import ru.strong.springoraclejournal.model.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record UserDto (
        Integer id,
        String name,
        String email,
        String phone,
        String address,
        Role role,
        LocalDateTime createdAt,
        BigDecimal balance,
        Boolean receiveReceipts
){
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public String getCreatedAtFormatted() {
        return createdAt == null ? null : createdAt.format(FORMATTER);
    }


}
