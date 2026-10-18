package ru.strong.springoraclejournal.mapper;

import org.springframework.stereotype.Component;
import ru.strong.springoraclejournal.dto.CartItemDto;
import ru.strong.springoraclejournal.dto.ProductDto;
import ru.strong.springoraclejournal.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {
    public CartItemDto modelToDto(CartItem cartItem) {
        var p = cartItem.getProduct();
        ProductDto productDto = new ProductDto(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getCategory(),
                p.getImageUrl(),
                p.getAvailable()
        );
        BigDecimal lineTotal = p.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        return new CartItemDto(cartItem.getId(), productDto, cartItem.getQuantity(), lineTotal);
    }

    public List<CartItemDto> modelToDto(List<CartItem> items) {
        return items.stream()
                .map(this::modelToDto)
                .toList();
    }
}
