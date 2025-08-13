package com.omega.cuentas.cuenta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta", uniqueConstraints = {
    @UniqueConstraint(columnNames = "numeroCuenta")})
@Data //Genera automáticamente getters, setters, toString, equals, hashCode
@NoArgsConstructor //Crea un constructor vacío (sin parámetros)
@AllArgsConstructor //Crea un constructor con todos los campos
@Builder //Permite construir objetos con una sintaxis fluida (Cuenta.builder()...)
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "numeroCuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoCuenta", nullable = false)
    private TipoCuenta tipoCuenta; // Ej: AHORROS, CORRIENTE

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "saldoInicial", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "saldoDisponible", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldoDisponible;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado; // Ej: ACTIVA, INACTIVA, BLOQUEADA

    @NotNull
    @Column(name = "clienteId", nullable = false)
    private Long clienteId;

    //Métodos de la entidad
    public boolean estaActiva() {
        return Boolean.TRUE.equals(this.estado);
    }

    public boolean estaInactiva() {
        return Boolean.FALSE.equals(this.estado);
    }

    //Getters y Setters
}
