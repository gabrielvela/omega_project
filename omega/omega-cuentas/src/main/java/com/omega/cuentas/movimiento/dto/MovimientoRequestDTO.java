/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omega.cuentas.movimiento.dto;

import com.omega.cuentas.movimiento.model.TipoMovimiento;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Armando Gabriel
 */
@Data
public class MovimientoRequestDTO {

    private String numeroCuenta; // ✅ nuevo campo
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private Date fecha;

    // Getters y setters
}
