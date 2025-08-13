package com.omega.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id", unique = true, nullable = false)
    private Long clienteId;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(name= "contrasenia", nullable = false)
    private String contrasenia;

    @NotNull(message = "El estado no puede ser nulo")
    @Column(name= "estado", nullable = false)
    private Boolean estado;   

    // Getters y Setters
   
}
