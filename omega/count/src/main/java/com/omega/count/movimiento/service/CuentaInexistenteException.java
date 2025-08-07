package com.omega.count.movimiento.service;

public class CuentaInexistenteException extends RuntimeException {
    public CuentaInexistenteException(String mensaje) {
        super(mensaje);
    }
}
