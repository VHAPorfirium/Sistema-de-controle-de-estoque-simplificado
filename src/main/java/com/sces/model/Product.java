package com.sces.model;

import java.util.Objects;

public class Product {
    private final long id;
    private final String name;
    private final String description;
    private final int quantity;

    public Product(long id, String name, String description, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("#%d | %s | qtd=%d%s",
                id, name, quantity,
                (description == null || description.isBlank()) ? "" : " | " + description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product that = (Product) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
