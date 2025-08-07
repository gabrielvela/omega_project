package com.omega.count.movimiento.service;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

