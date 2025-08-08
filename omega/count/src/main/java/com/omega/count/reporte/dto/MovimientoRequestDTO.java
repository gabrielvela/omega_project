package com.omega.count.reporte.dto;

import com.omega.count.movimiento.model.TipoMovimiento;

import java.math.BigDecimal;
import java.util.Date;

public class MovimientoRequestDTO {
    private TipoMovimiento tipo;
    private BigDecimal valor;
    private Date fecha;

    // Getters y setters

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
