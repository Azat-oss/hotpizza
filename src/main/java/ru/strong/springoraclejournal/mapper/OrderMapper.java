package ru.strong.springoraclejournal.mapper;

import org.springframework.stereotype.Component;
import ru.strong.springoraclejournal.dto.OrderDto;
import ru.strong.springoraclejournal.dto.OrderItemDto;
import ru.strong.springoraclejournal.model.Order;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderMapper {
    public OrderDto modelToDto(Order order) {
        List<OrderItemDto> items = order.getItems().stream()
                .map(i -> new OrderItemDto(
                        i.getProductName(),
                        i.getImageUrl(),
                        i.getUnitPrice(),
                        i.getQuantity(),
                        i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity()))
                ))
                .toList();
        return new OrderDto(
                order.getId(),
                order.getCreatedAt(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getShippingAddress(),
                order.getPhone(),
                items
        );
    }

    public List<OrderDto> modelToDto(List<Order> orders) {
        return orders.stream()
                .map(this::modelToDto)
                .toList();
    }
}
