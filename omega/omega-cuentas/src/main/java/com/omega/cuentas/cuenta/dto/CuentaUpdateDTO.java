package com.omega.cuentas.cuenta.dto;

import com.omega.cuentas.cuenta.model.TipoCuenta;

public class CuentaUpdateDTO {
    private TipoCuenta tipoCuenta;
    private Boolean estado;

    // Getters y setters


    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
