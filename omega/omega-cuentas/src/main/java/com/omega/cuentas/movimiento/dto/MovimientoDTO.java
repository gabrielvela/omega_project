package com.omega.cuentas.movimiento.dto;

import com.omega.cuentas.movimiento.model.Movimiento;
import com.omega.cuentas.movimiento.model.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Data;

@Data
public class MovimientoDTO {

    private Long id;
    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private String numeroCuenta;

    public MovimientoDTO() {
        
    }

    public MovimientoDTO(Movimiento movimiento) {
        this.id = movimiento.getId();
        this.fecha = movimiento.getFecha().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        this.tipoMovimiento = movimiento.getTipoMovimiento();
        this.valor = movimiento.getValor();
        this.saldo = movimiento.getSaldo();
        this.numeroCuenta = movimiento.getCuenta().getNumeroCuenta();
    }

}
