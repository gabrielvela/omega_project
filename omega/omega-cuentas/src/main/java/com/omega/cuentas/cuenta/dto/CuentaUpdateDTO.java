package com.omega.cuentas.cuenta.dto;

import com.omega.cuentas.cuenta.model.TipoCuenta;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CuentaUpdateDTO {

    @NotNull(message = "El tipo de cuenta no puede ser nulo")
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean estado;

    @NotNull(message = "El número de cuenta no puede ser nulo")
    private String numeroCuenta;

}
