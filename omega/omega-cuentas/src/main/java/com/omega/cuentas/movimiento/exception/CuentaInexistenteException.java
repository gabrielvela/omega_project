package com.omega.cuentas.movimiento.exception;

public class CuentaInexistenteException extends RuntimeException {
    public CuentaInexistenteException(String mensaje) {
        super(mensaje);
    }
}
