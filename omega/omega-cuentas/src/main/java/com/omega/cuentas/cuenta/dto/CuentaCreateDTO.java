package com.omega.cuentas.cuenta.dto;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;

import java.math.BigDecimal;

public class CuentaCreateDTO {
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private String nombreCliente; //

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
    
    public static Cuenta transformarDTOaCuenta(CuentaCreateDTO dto){
        
        cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setSaldoDisponible(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());
        cuenta.setEstado(true);
//        cuenta.setClienteId(dto.getIdCliente());
        
        return cuenta;        
    }
    
    private static CuentaCreateDTO dto;
    public static CuentaCreateDTO transformarCuentaADTO(Cuenta c){
        dto = new CuentaCreateDTO();
        
        dto.setEstado(c.getEstado());
//        dto.setNombreCliente(c.getClienteId());
        dto.setNumeroCuenta(c.getNumeroCuenta());
        dto.setSaldoInicial(c.getSaldoInicial());
        dto.setTipoCuenta(c.getTipoCuenta());
        
        return dto;
    }
}
