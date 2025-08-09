package com.omega.user.dto;

import com.omega.user.model.Cliente;

public class ClienteDTO {
    private Long clienteId;
    private String nombre;

    public ClienteDTO(Cliente cliente) {
        this.clienteId = cliente.getClienteId();
        this.nombre = cliente.getNombre();
    }

    // Getters y setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
