package com.omega.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@MappedSuperclass//Permite heredar elementos sin crear otra tabla en la db
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long personaId;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "El género no puede estar vacío")
    private String genero;

    @Min(value = 0, message = "La edad no puede ser negativa")
    private int edad;

    @NotBlank(message = "La identificación no puede estar vacía")
    @Column(unique = true, nullable = false)// Clave única en la base
    private String identificacion;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;


    // Getters y setters
}
