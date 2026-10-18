package ru.strong.springoraclejournal.mapper;

import org.springframework.stereotype.Component;
import ru.strong.springoraclejournal.dto.UserDto;
import ru.strong.springoraclejournal.dto.UserForm;
import ru.strong.springoraclejournal.model.Role;
import ru.strong.springoraclejournal.model.User;

import java.util.List;

@Component

public class UserMapper {
    public User formToModel(UserForm form, String encodedPassword, Role role) {
        return User.builder()
                .id(form.id())
                .name(form.name())
                .email(form.email())
                .password(encodedPassword)
                .phone(form.phone())
                .address(form.address())
                .role(role)
                .build();
    }

    public void updateModelFromForm(User user, UserForm form) {
        user.setName(form.name());
        user.setEmail(form.email());
        user.setPhone(form.phone());
        user.setAddress(form.address());
    }

    public UserForm modelToForm(User model) {
        return new UserForm(
                model.getId(),
                model.getName(),
                model.getEmail(),
                null,
                model.getPhone(),
                model.getAddress()
        );
    }

    public UserDto modelToDto(User model) {
        return new UserDto(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getPhone(),
                model.getAddress(),
                model.getRole(),
                model.getCreatedAt()
        );
    }

    public List<UserDto> modelToDto(List<User> models) {
        return models.stream()
                .map(this::modelToDto)
                .toList();
    }
}
