package ru.strong.springoraclejournal.controller;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.strong.springoraclejournal.dto.ProductForm;
import ru.strong.springoraclejournal.model.Category;
import ru.strong.springoraclejournal.service.ProductService;
import ru.strong.springoraclejournal.service.UserService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;

    public AdminController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("productCount", productService.findAll().size());
        model.addAttribute("userCount", userService.findAll().size());
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        model.addAttribute("productForm", new ProductForm(
                null, "", "", BigDecimal.ZERO, Category.PIZZA, "", true
        ));
        model.addAttribute("categories", List.of(Category.values()));
        return "admin/product-form";
    }

    @PostMapping("/products")
    public String createProduct(@Valid @ModelAttribute("productForm") ProductForm form,
                                BindingResult result,
                                Model model,
                                RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("categories", List.of(Category.values()));
            return "admin/product-form";
        }
        productService.save(form);
        attrs.addFlashAttribute("success", "Продукт добавлен");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/{id}/edit")
    public String editProductForm(@PathVariable Integer id, Model model) {
        var p = productService.findById(id);
        model.addAttribute("productForm", new ProductForm(
                p.id(), p.name(), p.description(), p.price(), p.category(), p.imageUrl(), p.available()
        ));
        model.addAttribute("categories", List.of(Category.values()));
        model.addAttribute("productId", id);
        return "admin/product-form";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Integer id,
                                @Valid @ModelAttribute("productForm") ProductForm form,
                                BindingResult result,
                                Model model,
                                RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("categories", List.of(Category.values()));
            model.addAttribute("productId", id);
            return "admin/product-form";
        }
        productService.update(id, form);
        attrs.addFlashAttribute("success", "Продукт обновлён");
        return "redirect:/admin/products";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Integer id, RedirectAttributes attrs) {
        try {
            productService.deleteById(id);
            attrs.addFlashAttribute("success", "Продукт удалён");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes attrs) {
        try {
            userService.deleteById(id);
            attrs.addFlashAttribute("success", "Пользователь удалён");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
}
