package com.omega.cuentas.movimiento.service;

public class CuentaInexistenteException extends RuntimeException {
    public CuentaInexistenteException(String mensaje) {
        super(mensaje);
    }
}
