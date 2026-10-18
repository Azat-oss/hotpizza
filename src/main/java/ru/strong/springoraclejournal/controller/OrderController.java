package ru.strong.springoraclejournal.controller;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.strong.springoraclejournal.config.CustomUserDetails;
import ru.strong.springoraclejournal.dto.CheckoutForm;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.service.CartService;
import ru.strong.springoraclejournal.service.OrderService;

@Controller
@RequestMapping("/orders")

public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping
    public String list(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("orders", orderService.findUserOrders(user));
        return "orders/list";
    }

    @GetMapping("/checkout")
    public String checkoutForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        var items = cartService.getCartItems(user);
        if (items.isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("items", items);
        model.addAttribute("total", cartService.getCartTotal(user));
        model.addAttribute("checkoutForm", new CheckoutForm(
                user.getAddress() == null ? "" : user.getAddress(),
                user.getPhone() == null ? "" : user.getPhone()
        ));
        return "orders/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("checkoutForm") CheckoutForm form,
                           BindingResult result,
                           @AuthenticationPrincipal CustomUserDetails userDetails,
                           RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "orders/checkout";
        }
        try {
            orderService.createFromCart(userDetails.getUser(), form.shippingAddress(), form.phone());
            attrs.addFlashAttribute("success", "Заказ создан. Оплатите его в истории заказов.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/orders";
    }

    @PostMapping("/{id}/pay")
    public String pay(@PathVariable Integer id,
                      @AuthenticationPrincipal CustomUserDetails userDetails,
                      RedirectAttributes attrs) {
        try {
            orderService.pay(id, userDetails.getUser());
            attrs.addFlashAttribute("success", "Заказ оплачен");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/orders";
    }
}
