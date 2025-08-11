package com.omega.cuentas.cuenta.dto;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;
import java.math.BigDecimal;

public class CuentaUpdateDTO {
    private TipoCuenta tipoCuenta;
    private Boolean estado;
    
    private String numeroCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;

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
    
    private static Cuenta cuenta;
    public static Cuenta transformarDTOaCuenta(Cuenta c, CuentaUpdateDTO dto){
//        cuenta = new Cuenta();
        cuenta = c;
        
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setEstado(dto.getEstado());
        cuenta.setSaldoDisponible(dto.getSaldoDisponible());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
//        cuenta.setClienteId(dto.getNombreCliente());

        return cuenta;
    }
    
    private static CuentaUpdateDTO dto;
    public static CuentaUpdateDTO transformarCuentaADTO(Cuenta c, String nombreCliente){
        dto = new CuentaUpdateDTO();
        
        dto.setNumeroCuenta(c.getNumeroCuenta());
        dto.setEstado(c.getEstado());
        dto.setSaldoDisponible(c.getSaldoDisponible());
        dto.setSaldoInicial(c.getSaldoInicial());
        dto.setTipoCuenta(c.getTipoCuenta());
        
        return dto;
    }
    
}
