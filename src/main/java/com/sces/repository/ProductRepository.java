package com.sces.repository;

import java.util.List;
import java.util.Optional;

import com.sces.model.Product;

public interface ProductRepository {
    long nextId();
    Product save(Product product);
    Optional<Product> findByName(String normalizedName);
    Optional<Product> findById(long id); 
    List<Product> findAll();
}
