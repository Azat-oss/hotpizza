package ru.strong.springoraclejournal.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.strong.springoraclejournal.config.CustomUserDetails;
import ru.strong.springoraclejournal.dto.TopUpConfirmForm;
import ru.strong.springoraclejournal.dto.TopUpForm;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.service.EmailService;
import ru.strong.springoraclejournal.service.UserService;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;

@Controller
@RequestMapping("/profile/balance")
public class BalanceController {
    private final UserService userService;
    private final EmailService emailService;
    private final SecureRandom random = new SecureRandom();

    public BalanceController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping
    public String topUpForm(Model model) {
        model.addAttribute("topUpForm", new TopUpForm("", BigDecimal.ZERO));
        return "users/topup";
    }

    @PostMapping
    public String requestTopUp(@Valid @ModelAttribute("topUpForm") TopUpForm form,
                               BindingResult result,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               HttpSession session,
                               RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "users/topup";
        }

        User user = userService.findById(userDetails.getUser().getId());

        // Генерируем 6-значный код
        String code = String.format("%06d", random.nextInt(1000000));

        // Сохраняем данные в сессии
        session.setAttribute("topup_card", form.cardNumber());
        session.setAttribute("topup_amount", form.amount());
        session.setAttribute("topup_code", code);

        // "Отправляем" код на email
        emailService.sendVerificationCode(user.getEmail(), user.getName(), code);

        attrs.addFlashAttribute("info", "Код подтверждения отправлен на вашу почту (смотрите лог консоли)");
        return "redirect:/profile/balance/confirm";
    }

    @GetMapping("/confirm")
    public String confirmForm(Model model) {
        model.addAttribute("confirmForm", new TopUpConfirmForm(""));
        return "users/confirm-topup";
    }

    @PostMapping("/confirm")
    public String confirmTopUp(@Valid @ModelAttribute("confirmForm") TopUpConfirmForm form,
                               BindingResult result,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               HttpSession session,
                               RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "users/confirm-topup";
        }

        String expectedCode = (String) session.getAttribute("topup_code");
        if (expectedCode == null) {
            attrs.addFlashAttribute("error", "Сессия истекла. Начните заново.");
            return "redirect:/profile/balance";
        }

        if (!expectedCode.equals(form.code())) {
            result.rejectValue("code", "mismatch", "Неверный код подтверждения");
            return "users/confirm-topup";
        }

        // Код верный — пополняем баланс
        BigDecimal amount = (BigDecimal) session.getAttribute("topup_amount");
        userService.topUpBalance(userDetails.getUser().getId(), amount);

        // Очищаем сессию
        session.removeAttribute("topup_card");
        session.removeAttribute("topup_amount");
        session.removeAttribute("topup_code");

        attrs.addFlashAttribute("success", "Баланс пополнен на " + amount + " ₽");
        return "redirect:/profile";
    }
}
