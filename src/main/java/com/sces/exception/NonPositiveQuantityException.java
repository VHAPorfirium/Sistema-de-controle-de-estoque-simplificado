package com.sces.exception;

public class NonPositiveQuantityException extends RuntimeException {
    public NonPositiveQuantityException(int qty) {
        super("Quantidade a adicionar deve ser maior que zero: " + qty);
    }
}