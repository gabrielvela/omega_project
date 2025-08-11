package com.omega.cuentas.movimiento.model;

import com.omega.cuentas.cuenta.model.Cuenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movimiento")
@Data //Genera automáticamente getters, setters, toString, equals, hashCode
@NoArgsConstructor //Crea un constructor vacío (sin parámetros)
@AllArgsConstructor //Crea un constructor con todos los campos
@Builder //Permite construir objetos con una sintaxis fluida (Movimiento.builder()...)
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipoMovimiento;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private BigDecimal saldo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    // Getters, setters, constructor y toString si lo deseas
}
