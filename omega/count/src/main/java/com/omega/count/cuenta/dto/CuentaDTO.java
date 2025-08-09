package com.omega.count.cuenta.dto;

import com.omega.count.cuenta.model.TipoCuenta;

import java.math.BigDecimal;

public class CuentaDTO {
    private Long id;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private Boolean estado;
    private Long clienteId; // Este viene del otro proyecto user

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }

    public BigDecimal getSaldoDisponible() { return saldoDisponible; }
    public void setSaldoDisponible(BigDecimal saldoDisponible) { this.saldoDisponible = saldoDisponible; }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}