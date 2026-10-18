package ru.strong.springoraclejournal.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.strong.springoraclejournal.config.CustomUserDetails;
import ru.strong.springoraclejournal.dto.CartItemDto;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.service.CartService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        List<CartItemDto> items = cartService.getCartItems(user);
        BigDecimal total = cartService.getCartTotal(user);
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        return "cart/view";
    }

    @PostMapping("/add/{productId}")
    public String add(@PathVariable Integer productId,
                      @RequestParam(defaultValue = "1") int quantity,
                      @AuthenticationPrincipal CustomUserDetails userDetails,
                      RedirectAttributes attrs) {
        try {
            cartService.addToCart(userDetails.getUser(), productId, quantity);
            attrs.addFlashAttribute("success", "Добавлено в корзину");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products";
    }

    @PostMapping("/update/{cartItemId}")
    public String updateQuantity(@PathVariable Integer cartItemId,
                                 @RequestParam int quantity,
                                 @AuthenticationPrincipal CustomUserDetails userDetails,
                                 RedirectAttributes attrs) {
        try {
            cartService.updateQuantity(userDetails.getUser(), cartItemId, quantity);
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove/{cartItemId}")
    public String remove(@PathVariable Integer cartItemId,
                         @AuthenticationPrincipal CustomUserDetails userDetails,
                         RedirectAttributes attrs) {
        try {
            cartService.removeFromCart(userDetails.getUser(), cartItemId);
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }
}
