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

import java.util.List;

@Service
@Transactional

public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(UserForm form) {
        String encoded = passwordEncoder.encode(form.password());
        User user = userMapper.formToModel(form, encoded, Role.USER);
        userRepository.save(user);
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
        if (form.password() != null && !form.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(form.password()));
        }
        userRepository.save(user);
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
