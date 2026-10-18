package ru.strong.springoraclejournal.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strong.springoraclejournal.dto.CartItemDto;
import ru.strong.springoraclejournal.mapper.CartMapper;
import ru.strong.springoraclejournal.model.CartItem;
import ru.strong.springoraclejournal.model.Product;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.repository.CartItemRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartMapper cartMapper;

    public CartService(CartItemRepository cartItemRepository,
                       ProductService productService,
                       CartMapper cartMapper) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.cartMapper = cartMapper;
    }

    public List<CartItemDto> getCartItems(User user) {
        return cartMapper.modelToDto(cartItemRepository.findByUserOrderByProduct_NameAsc(user));
    }

    public int countItems(User user) {
        return (int) cartItemRepository.countByUser(user);
    }

    public BigDecimal getCartTotal(User user) {
        return cartItemRepository.findByUserOrderByProduct_NameAsc(user).stream()
                .map(ci -> ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void addToCart(User user, Integer productId, int quantity) {
        Product product = productService.getProductById(productId);
        Optional<CartItem> existing = cartItemRepository.findByUserAndProduct(user, product);
        if (existing.isPresent()) {
            CartItem ci = existing.get();
            ci.setQuantity(ci.getQuantity() + quantity);
            cartItemRepository.save(ci);
        } else {
            CartItem ci = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(ci);
        }
    }

    @Transactional
    public void updateQuantity(User user, Integer cartItemId, int quantity) {
        CartItem ci = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Элемент корзины не найден: " + cartItemId));
        if (!ci.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Нет доступа к этому элементу корзины");
        }
        if (quantity <= 0) {
            cartItemRepository.delete(ci);
        } else {
            ci.setQuantity(quantity);
            cartItemRepository.save(ci);
        }
    }

    @Transactional
    public void removeFromCart(User user, Integer cartItemId) {
        CartItem ci = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Элемент корзины не найден: " + cartItemId));
        if (!ci.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Нет доступа к этому элементу корзины");
        }
        cartItemRepository.delete(ci);
    }

    @Transactional
    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }
}
