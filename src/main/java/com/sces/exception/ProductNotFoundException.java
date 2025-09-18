package com.sces.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long id) {
        super("Produto não encontrado para o ID: " + id);
    }
}