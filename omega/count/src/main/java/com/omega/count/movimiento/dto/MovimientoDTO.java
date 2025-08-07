package com.omega.count.movimiento.dto;

import com.omega.count.movimiento.model.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class MovimientoDTO {
    private Long cuentaId;
    private TipoMovimiento tipo;
    private BigDecimal valor;
    private Date fecha;
    // Getters y setters


    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
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
