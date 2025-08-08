package com.omega.count.reporte.dto;

import com.omega.count.movimiento.dto.MovimientoDTO;

import java.math.BigDecimal;
import java.util.List;

public class CuentaResumenDTO {
    private String numeroCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private List<MovimientoRequestDTO> movimientos;

    // Getters y setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public List<MovimientoRequestDTO> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoRequestDTO> movimientos) {
        this.movimientos = movimientos;
    }
}
