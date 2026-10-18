package ru.strong.springoraclejournal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strong.springoraclejournal.model.CartItem;
import ru.strong.springoraclejournal.model.Product;
import ru.strong.springoraclejournal.model.User;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository  extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUserOrderByProduct_NameAsc(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
    void deleteByUser(User user);
    long countByUser(User user);
}
