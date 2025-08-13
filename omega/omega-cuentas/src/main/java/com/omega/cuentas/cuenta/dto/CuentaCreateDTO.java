package com.omega.cuentas.cuenta.dto;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;
import com.omega.cuentas.integration.dto.Cliente;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CuentaCreateDTO {

    @NotBlank
    private String numeroCuenta;

    @NotNull
    private TipoCuenta tipoCuenta;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal saldoInicial;

    private Cliente cliente;

    private Cuenta cuenta;

    public CuentaCreateDTO() {
    }

    public CuentaCreateDTO(Cuenta cuenta, Cliente cliente) {
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.tipoCuenta = cuenta.getTipoCuenta();
        this.saldoInicial = cuenta.getSaldoInicial();
        this.cliente = cliente;
        this.cuenta = cuenta;
    }

    public Cuenta transformarDTOaCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setSaldoInicial(saldoInicial);
        cuenta.setSaldoDisponible(saldoInicial);
        cuenta.setEstado(Boolean.TRUE);
        cuenta.setClienteId(cliente.getClienteId());
        return cuenta;
    }
}
