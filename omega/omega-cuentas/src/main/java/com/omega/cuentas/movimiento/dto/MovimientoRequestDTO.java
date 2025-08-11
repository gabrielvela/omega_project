/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omega.cuentas.movimiento.dto;

import com.omega.cuentas.movimiento.model.TipoMovimiento;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Armando Gabriel
 */
public class MovimientoRequestDTO {
    private String numeroCuenta; // ✅ nuevo campo
    private TipoMovimiento tipo;
    private BigDecimal valor;
    private Date fecha;

    // Getters y setters

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
