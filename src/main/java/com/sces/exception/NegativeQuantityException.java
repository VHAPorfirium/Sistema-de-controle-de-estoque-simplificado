package com.sces.exception;

public class NegativeQuantityException extends RuntimeException {
    public NegativeQuantityException(int qty) {
        super("Quantidade inicial não pode ser negativa: " + qty);
    }
}
