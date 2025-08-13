package com.omega.clientes.dto;

import com.omega.clientes.model.Cliente;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClienteDTO {

    private Long clienteId;
    private String nombre;
    private String identificacion;
    private String direccion;
    private String telefono;
    private Boolean estado;
    private String genero;
    private int edad;
    private String contrasenia;

    public ClienteDTO(Cliente cliente) {
        this.clienteId = cliente.getClienteId();
        this.nombre = cliente.getNombre();
        this.identificacion = cliente.getIdentificacion();
        this.direccion = cliente.getDireccion();
        this.telefono = cliente.getTelefono();
        this.estado = cliente.getEstado();
        this.genero = cliente.getGenero();
        this.edad = cliente.getEdad();
        this.contrasenia = cliente.getContrasenia();
    }

    public ClienteDTO(Long clienteId, String nombre, String identificacion, String direccion, String telefono, Boolean estado, String genero, int edad, String contrasenia) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = estado;
        this.genero = genero;
        this.edad = edad;
        this.contrasenia = contrasenia;
    }

    private Cliente c;

    public Cliente transformarDtoACliente() {
        // convertir a Cliente
        c = new Cliente();
        c.setClienteId(clienteId);
        c.setContrasenia(contrasenia);
        c.setDireccion(direccion);
        c.setEdad(edad);
        c.setEstado(estado);
        c.setGenero(genero);
        c.setIdentificacion(identificacion);
        c.setNombre(nombre);
        c.setTelefono(telefono);

        return c;
    }

//    Getters y Setters
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }


}
