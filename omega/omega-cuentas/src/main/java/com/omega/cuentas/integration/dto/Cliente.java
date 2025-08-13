package com.omega.cuentas.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    private Long clienteId;
    private String nombre;
    private String identificacion;
    private String direccion;
    private String telefono;
    private Boolean estado;
    private String genero;
    private Integer edad;

    public Cliente(Long clienteId) {
        this.clienteId = clienteId;
    }

}
