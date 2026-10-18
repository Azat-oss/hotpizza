package ru.strong.springoraclejournal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strong.springoraclejournal.model.Order;
import ru.strong.springoraclejournal.model.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
