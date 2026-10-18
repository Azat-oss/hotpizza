package ru.strong.springoraclejournal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strong.springoraclejournal.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
