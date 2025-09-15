package com.sces.service;

import java.util.List;

import com.sces.model.Product;

public interface ProductService {
    Product createProduct(String name, String description, int initialQuantity);

    List<Product> listAll();
}
