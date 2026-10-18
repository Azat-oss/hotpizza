package ru.strong.springoraclejournal.mapper;

import org.springframework.stereotype.Component;
import ru.strong.springoraclejournal.dto.ProductDto;
import ru.strong.springoraclejournal.dto.ProductForm;
import ru.strong.springoraclejournal.model.Product;

import java.util.List;

@Component
public class ProductMapper {
    public Product formToModel(ProductForm form) {
        return Product.builder()
                .id(form.id())
                .name(form.name())
                .description(form.description())
                .price(form.price())
                .category(form.category())
                .imageUrl(form.imageUrl())
                .available(form.available() != null ? form.available() : Boolean.TRUE)
                .build();
    }

    public void updateModelFromForm(Product product, ProductForm form) {
        product.setName(form.name());
        product.setDescription(form.description());
        product.setPrice(form.price());
        product.setCategory(form.category());
        product.setImageUrl(form.imageUrl());
        if (form.available() != null) {
            product.setAvailable(form.available());
        }
    }

    public ProductForm modelToForm(Product model) {
        return new ProductForm(
                model.getId(),
                model.getName(),
                model.getDescription(),
                model.getPrice(),
                model.getCategory(),
                model.getImageUrl(),
                model.getAvailable()
        );
    }

    public ProductDto modelToDto(Product model) {
        return new ProductDto(
                model.getId(),
                model.getName(),
                model.getDescription(),
                model.getPrice(),
                model.getCategory(),
                model.getImageUrl(),
                model.getAvailable()
        );
    }

    public List<ProductDto> modelToDto(List<Product> models) {
        return models.stream()
                .map(this::modelToDto)
                .toList();
    }
}
