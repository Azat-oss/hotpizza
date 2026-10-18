package ru.strong.springoraclejournal.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strong.springoraclejournal.dto.OrderDto;
import ru.strong.springoraclejournal.mapper.OrderMapper;
import ru.strong.springoraclejournal.model.CartItem;
import ru.strong.springoraclejournal.model.Order;
import ru.strong.springoraclejournal.model.OrderItem;
import ru.strong.springoraclejournal.model.OrderStatus;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.repository.CartItemRepository;
import ru.strong.springoraclejournal.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        CartItemRepository cartItemRepository,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDto> findUserOrders(User user) {
        return orderMapper.modelToDto(orderRepository.findByUserOrderByCreatedAtDesc(user));
    }

    public OrderDto findById(Integer id) {
        return orderMapper.modelToDto(getOrderById(id));
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден: " + id));
    }

    @Transactional
    public OrderDto createFromCart(User user, String shippingAddress, String phone) {
        List<CartItem> cartItems = cartItemRepository.findByUserOrderByProduct_NameAsc(user);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Корзина пуста");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cartItems) {
            BigDecimal lineTotal = ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));
            total = total.add(lineTotal);

            OrderItem oi = OrderItem.builder()
                    .productName(ci.getProduct().getName())
                    .unitPrice(ci.getProduct().getPrice())
                    .quantity(ci.getQuantity())
                    .imageUrl(ci.getProduct().getImageUrl())
                    .build();
            orderItems.add(oi);
        }

        Order order = Order.builder()
                .user(user)
                .totalPrice(total)
                .status(OrderStatus.PENDING)
                .shippingAddress(shippingAddress)
                .phone(phone)
                .build();

        for (OrderItem oi : orderItems) {
            oi.setOrder(order);
            order.getItems().add(oi);
        }

        Order saved = orderRepository.save(order);
        cartItemRepository.deleteByUser(user);

        return orderMapper.modelToDto(saved);
    }

    @Transactional
    public void pay(Integer orderId, User user) {
        Order order = getOrderById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Это не ваш заказ");
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Заказ уже обработан");
        }
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }
}
