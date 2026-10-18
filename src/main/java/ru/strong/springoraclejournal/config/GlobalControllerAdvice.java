package ru.strong.springoraclejournal.config;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.strong.springoraclejournal.dto.UserDto;
import ru.strong.springoraclejournal.mapper.UserMapper;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.repository.UserRepository;
import ru.strong.springoraclejournal.service.CartService;

@ControllerAdvice

public class GlobalControllerAdvice {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CartService cartService;

    public GlobalControllerAdvice(UserRepository userRepository,
                                  UserMapper userMapper,
                                  CartService cartService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.cartService = cartService;
    }

    @ModelAttribute("currentUser")
    public UserDto currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails cud) {
            User user = userRepository.findById(cud.getUser().getId()).orElse(cud.getUser());
            return userMapper.modelToDto(user);
        }
        return null;
    }

    @ModelAttribute("cartCount")
    public Integer cartCount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return 0;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails cud) {
            try {
                return cartService.countItems(cud.getUser());
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
}
