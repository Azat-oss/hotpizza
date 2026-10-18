package ru.strong.springoraclejournal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import ru.strong.springoraclejournal.model.Category;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ru.strong.springoraclejournal.service.ProductService productService;

    public ProductController(ru.strong.springoraclejournal.service.ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Category category,
                       @RequestParam(required = false) String search,
                       Model model) {
        model.addAttribute("products", productService.findFiltered(category, search));
        model.addAttribute("categories", List.of(Category.values()));
        model.addAttribute("selectedCategory", category);
        model.addAttribute("search", search == null ? "" : search);
        return "products/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "products/view";
    }
}
