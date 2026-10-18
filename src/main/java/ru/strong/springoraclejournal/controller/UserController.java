package ru.strong.springoraclejournal.controller;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.strong.springoraclejournal.config.CustomUserDetails;
import ru.strong.springoraclejournal.dto.UserDto;
import ru.strong.springoraclejournal.dto.UserForm;
import ru.strong.springoraclejournal.mapper.UserMapper;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.service.UserService;

@Controller
@RequestMapping("/profile")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User u = userService.findById(userDetails.getUser().getId());
        UserDto dto = userMapper.modelToDto(u);
        model.addAttribute("user", dto);
        return "users/profile";
    }

    @GetMapping("/edit")
    public String editForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User u = userService.findById(userDetails.getUser().getId());
        model.addAttribute("userForm", new UserForm(
                u.getId(), u.getName(), u.getEmail(), null, u.getPhone(), u.getAddress(),
                u.getReceiveReceipts()
        ));
        return "users/edit";

    }

    @PostMapping
    public String update(@Valid @ModelAttribute("userForm") UserForm form,
                         BindingResult result,
                         @AuthenticationPrincipal CustomUserDetails userDetails,
                         RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "users/edit";
        }
        userService.updateProfile(userDetails.getUser().getId(), form);
        attrs.addFlashAttribute("success", "Профиль обновлён");
        return "redirect:/profile";
    }
}
