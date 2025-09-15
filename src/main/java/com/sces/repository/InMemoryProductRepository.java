package com.sces.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.sces.model.Product;

public class InMemoryProductRepository implements ProductRepository {
    private final AtomicLong seq = new AtomicLong(0L);
    private final Map<Long, Product> byId = new LinkedHashMap<>();
    private final Map<String, Long> nameIndex = new HashMap<>();

    @Override
    public long nextId() {
        return seq.incrementAndGet();
    }

    @Override
    public synchronized Product save(Product product) {
        byId.put(product.getId(), product);
        nameIndex.put(normalize(product.getName()), product.getId());
        return product;
    }

    @Override
    public synchronized Optional<Product> findByName(String normalizedName) {
        Long id = nameIndex.get(normalizedName);
        if (id == null)
            return Optional.empty();
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public synchronized List<Product> findAll() {
        return new ArrayList<>(byId.values());
    }

    public static String normalize(String name) {
        return name == null ? null : name.trim().toLowerCase(Locale.ROOT);
    }
}
