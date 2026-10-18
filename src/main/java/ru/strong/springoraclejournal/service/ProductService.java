package ru.strong.springoraclejournal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strong.springoraclejournal.dto.ProductDto;
import ru.strong.springoraclejournal.dto.ProductForm;
import ru.strong.springoraclejournal.mapper.ProductMapper;
import ru.strong.springoraclejournal.model.Category;
import ru.strong.springoraclejournal.model.Product;
import ru.strong.springoraclejournal.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@Transactional(readOnly = true)

public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> findAll() {
        return productMapper.modelToDto(productRepository.findAll());
    }

    public List<ProductDto> findFiltered(Category category, String search) {
        List<Product> products;
        boolean hasCat = category != null;
        boolean hasSearch = search != null && !search.isBlank();

        if (hasCat && hasSearch) {
            products = productRepository.findByCategoryAndNameContainingIgnoreCase(category, search);
        } else if (hasCat) {
            products = productRepository.findByCategory(category);
        } else if (hasSearch) {
            products = productRepository.findByNameContainingIgnoreCase(search);
        } else {
            products = productRepository.findAll();
        }
        return productMapper.modelToDto(products);
    }

    public ProductDto findById(Integer id) {
        return productMapper.modelToDto(getProductById(id));
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продукт не найден: " + id));
    }

    @Transactional
    public ProductDto save(ProductForm form) {
        Product product = productMapper.formToModel(form);
        return productMapper.modelToDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto update(Integer id, ProductForm form) {
        Product product = getProductById(id);
        productMapper.updateModelFromForm(product, form);
        return productMapper.modelToDto(productRepository.save(product));
    }

    @Transactional
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}
