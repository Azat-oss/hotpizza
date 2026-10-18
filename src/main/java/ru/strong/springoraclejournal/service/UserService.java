package ru.strong.springoraclejournal.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strong.springoraclejournal.dto.UserDto;
import ru.strong.springoraclejournal.dto.UserForm;
import ru.strong.springoraclejournal.mapper.UserMapper;
import ru.strong.springoraclejournal.model.Role;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional

public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(UserForm form) {
        String encoded = passwordEncoder.encode(form.password());
        User user = userMapper.formToModel(form, encoded, Role.USER);
        user.setBalance(BigDecimal.ZERO); // Баланс = 0
        user.setReceiveReceipts(form.receiveReceipts() != null && form.receiveReceipts());
        userRepository.save(user);

        emailService.sendRegistrationEmail(user.getEmail(), user.getName());
    }

    public void createAdmin(UserForm form) {
        String encoded = passwordEncoder.encode(form.password());
        User user = userMapper.formToModel(form, encoded, Role.ADMIN);
        userRepository.save(user);
    }

    public void updateProfile(Integer id, UserForm form) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + id));
        userMapper.updateModelFromForm(user, form);
        user.setReceiveReceipts(form.receiveReceipts() != null && form.receiveReceipts());
        if (form.password() != null && !form.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(form.password()));
        }
        userRepository.save(user);
    }

    @Transactional
    public void topUpBalance(Integer userId, BigDecimal amount) {
        User user = findById(userId);
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
    }

    @Transactional
    public boolean deductFromBalance(Integer userId, BigDecimal amount) {
        User user = findById(userId);
        if (user.getBalance().compareTo(amount) < 0) {
            return false; // Недостаточно средств
        }
        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + id));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + email));
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userMapper.modelToDto(userRepository.findAll());
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }


}
