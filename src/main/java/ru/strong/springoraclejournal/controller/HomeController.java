package ru.strong.springoraclejournal.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.strong.springoraclejournal.model.Category;
import ru.strong.springoraclejournal.service.ProductService;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        var all = productService.findAll();
        var featured = all.stream().limit(8).toList();
        model.addAttribute("featured", featured);
        model.addAttribute("categories", List.of(Category.values()));
        return "home";
    }
}
