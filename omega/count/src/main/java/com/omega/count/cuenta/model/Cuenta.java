package com.omega.count.cuenta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuentas", uniqueConstraints = {@UniqueConstraint(columnNames = "numeroCuenta")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave única (ID técnica en la DB)

    @NotBlank
    @Size(max = 20)
    @Column(name = "numeroCuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @NotBlank
    @Size(max = 20)
    @Column(name = "tipoCuenta", nullable = false)
    private String tipoCuenta; // Ej: AHORROS, CORRIENTE

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "saldoInicial", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @NotBlank
    @Column(name = "estado", nullable = false)
    private String estado; // Ej: ACTIVA, INACTIVA, BLOQUEADA

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(max = 20) String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(@NotBlank @Size(max = 20) String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public @NotBlank @Size(max = 20) String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(@NotBlank @Size(max = 20) String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public @NotBlank String getEstado() {
        return estado;
    }

    public void setEstado(@NotBlank String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", saldoInicial=" + saldoInicial +
                ", estado='" + estado + '\'' +
                '}';
    }
}