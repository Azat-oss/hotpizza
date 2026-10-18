package ru.strong.springoraclejournal.controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.strong.springoraclejournal.dto.UserForm;
import ru.strong.springoraclejournal.service.UserService;

@Controller

public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
        return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userForm", new UserForm(null, "", "", "", "", "", null));
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userForm") UserForm form,
                           BindingResult result,
                           RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "register";
        }
        if (userService.existsByEmail(form.email())) {
            result.rejectValue("email", "duplicate", "Email уже зарегистрирован");
            return "register";
        }
        userService.register(form);
        attrs.addFlashAttribute("success", "Регистрация успешна. Войдите.");
        return "redirect:/login";
    }
}
