package com.sces.service;

import java.util.Comparator;
import java.util.List;

import com.sces.exception.DuplicateProductNameException;
import com.sces.exception.InvalidProductNameException;
import com.sces.exception.NegativeQuantityException;
import com.sces.model.Product;
import com.sces.repository.InMemoryProductRepository;
import com.sces.repository.ProductRepository;
import com.sces.exception.NonPositiveQuantityException;
import com.sces.exception.ProductNotFoundException;

public class DefaultProductService implements ProductService {

    private final ProductRepository repo;

    public DefaultProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product createProduct(String name, String description, int initialQuantity) {
        String normalized = InMemoryProductRepository.normalize(name);
        if (normalized == null || normalized.isBlank()) {
            throw new InvalidProductNameException();
        }
        if (initialQuantity < 0) {
            throw new NegativeQuantityException(initialQuantity);
        }
        repo.findByName(normalized).ifPresent(p -> {
            throw new DuplicateProductNameException(name);
        });

        long id = repo.nextId();
        Product p = new Product(id, name.trim(), description, initialQuantity);
        return repo.save(p);
    }

    @Override
    public List<Product> listAll() {
        return repo.findAll().stream()
                .sorted(Comparator.comparingLong(Product::getId))
                .toList();
    }

    @Override
    public Product addStock(long id, int units) { // <--- NOVO
        if (units <= 0) {
            throw new NonPositiveQuantityException(units);
        }
        Product existing = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Product updated = new Product(
                existing.getId(),
                existing.getName(),
                existing.getDescription(),
                existing.getQuantity() + units
        );
        return repo.save(updated);
    }
}
