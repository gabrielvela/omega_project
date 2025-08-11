package com.omega.cuentas.cuenta.dto;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;

import java.math.BigDecimal;

public class CuentaResponseDTO {
    private Long id;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private Boolean estado;
    private String nombreCliente;

    // Getters y setters
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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    private static Cuenta cuenta;
    public static Cuenta transformarDTOaCuenta(CuentaResponseDTO dto){
        cuenta = new Cuenta();
        
        cuenta.setId(dto.getId());
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setEstado(dto.getEstado());
        cuenta.setSaldoDisponible(dto.getSaldoDisponible());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
//        cuenta.setClienteId(dto.getNombreCliente());

        return cuenta;
    }
    
    private static CuentaResponseDTO dto;
    public static CuentaResponseDTO transformarCuentaADTO(Cuenta c, String nombreCliente){
        dto = new CuentaResponseDTO();
        
        dto.setId(c.getId());
        dto.setNumeroCuenta(c.getNumeroCuenta());
        dto.setEstado(c.getEstado());
        dto.setSaldoDisponible(c.getSaldoDisponible());
        dto.setSaldoInicial(c.getSaldoInicial());
        dto.setTipoCuenta(c.getTipoCuenta());
        dto.setNombreCliente(nombreCliente);
        
        return dto;
    }
}
