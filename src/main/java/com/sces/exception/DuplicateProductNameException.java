package com.sces.exception;

public class DuplicateProductNameException extends RuntimeException {
    public DuplicateProductNameException(String name) {
        super("Já existe produto com o nome: " + name);
    }
}
