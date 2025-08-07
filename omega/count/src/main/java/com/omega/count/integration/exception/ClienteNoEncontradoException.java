package com.omega.count.integration.exception;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(Long clienteId) {
        super("Cliente con ID " + clienteId + " no existe.");
    }
}