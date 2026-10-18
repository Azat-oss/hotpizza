package ru.strong.springoraclejournal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strong.springoraclejournal.model.Category;
import ru.strong.springoraclejournal.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryAndNameContainingIgnoreCase(Category category, String name);
    long countByCategory(Category category);
}
