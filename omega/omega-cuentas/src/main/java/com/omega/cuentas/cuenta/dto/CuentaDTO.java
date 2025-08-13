package com.omega.cuentas.cuenta.dto;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;

import java.math.BigDecimal;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
public class CuentaDTO {

    private Long id;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private Boolean estado;
    private Long clienteId; // Este viene del otro proyecto user

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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

    public CuentaDTO() {
    }

    public CuentaDTO(Cuenta cuenta) {
        // asignar campos desde cuenta
        this.clienteId = cuenta.getClienteId();
        this.estado = cuenta.getEstado();
        this.id = cuenta.getId();
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.saldoDisponible = cuenta.getSaldoDisponible();
        this.saldoInicial = cuenta.getSaldoInicial();
        this.tipoCuenta = cuenta.getTipoCuenta();
    }

    public CuentaDTO(Long id, String numeroCuenta, TipoCuenta tipoCuenta, BigDecimal saldoInicial, BigDecimal saldoDisponible, Boolean estado, Long clienteId) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.saldoDisponible = saldoDisponible;
        this.estado = estado;
        this.clienteId = clienteId;
    }

    public static CuentaDTO convertirCuentaADTO(Cuenta cuenta) {
        CuentaDTO dto = new CuentaDTO();
        dto.setId(cuenta.getId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setTipoCuenta(cuenta.getTipoCuenta());
        dto.setSaldoInicial(cuenta.getSaldoInicial());
        dto.setSaldoDisponible(cuenta.getSaldoDisponible());
        dto.setEstado(cuenta.getEstado());
        dto.setClienteId(cuenta.getClienteId());
        return dto;
    }

    public static Cuenta convertirDTOACuenta(CuentaDTO dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(dto.getId());
        cuenta.setClienteId(dto.getClienteId());
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setSaldoDisponible(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());
        return cuenta;
    }

}
