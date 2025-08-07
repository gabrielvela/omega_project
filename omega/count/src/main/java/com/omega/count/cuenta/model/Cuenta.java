package com.omega.count.cuenta.model;

import com.omega.count.cuenta.dto.CuentaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta", uniqueConstraints = {@UniqueConstraint(columnNames = "numeroCuenta")})
@Data //Genera automáticamente getters, setters, toString, equals, hashCode
@NoArgsConstructor //Crea un constructor vacío (sin parámetros)
@AllArgsConstructor //Crea un constructor con todos los campos
@Builder //Permite construir objetos con una sintaxis fluida (Cuenta.builder()...)
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

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "saldoDisponible", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldoDisponible;

    @NotBlank
    @Column(name = "estado", nullable = false)
    private String estado; // Ej: ACTIVA, INACTIVA, BLOQUEADA

    @NotNull
    @Column(name = "clienteId", nullable = false)
    private Long clienteId;


    public CuentaDTO convertirDTOACuenta(Cuenta cuenta) {
        CuentaDTO dto = new CuentaDTO();
        dto.setId(cuenta.getId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setTipoCuenta(cuenta.getTipoCuenta());
        dto.setSaldoInicial(cuenta.getSaldoInicial());
        dto.setSaldoDisponible(cuenta.getSaldoDisponible());
        dto.setEstado(cuenta.getEstado());
        dto.setClienteId(cuenta.getClienteId());
        return dto;
    }

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

    public @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public @NotNull Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(@NotNull Long clienteId) {
        this.clienteId = clienteId;
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