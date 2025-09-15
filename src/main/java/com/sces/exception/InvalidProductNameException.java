package com.sces.exception;

public class InvalidProductNameException extends RuntimeException {
    public InvalidProductNameException() {
        super("Nome de produto inválido (obrigatório e não pode ser vazio).");
    }
}
