/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omega.cuentas.reporte.dto;

import com.omega.cuentas.cuenta.model.TipoCuenta;
import com.omega.cuentas.movimiento.dto.MovimientoDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Armando Gabriel
 */
@Data
public class EstadoCuentaDTO {
    private String nombreCliente;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private BigDecimal saldoDisponible;
    private List<MovimientoDTO> movimientos = new ArrayList<>();
}
