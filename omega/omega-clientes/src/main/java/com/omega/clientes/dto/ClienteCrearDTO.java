/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omega.clientes.dto;

import com.omega.clientes.model.Cliente;
import lombok.*;

/**
 *
 * @author Armando Gabriel
 */
@Data
@NoArgsConstructor
public class ClienteCrearDTO {

    private String nombre;
    private String identificacion;
    private String direccion;
    private String telefono;
    private Boolean estado;
    private String genero;
    private int edad;
    private String contrasenia;

    public ClienteCrearDTO (Cliente cliente) {
        this.nombre = cliente.getNombre();
        this.identificacion = cliente.getIdentificacion();
        this.direccion = cliente.getDireccion();
        this.telefono = cliente.getTelefono();
        this.estado = cliente.getEstado();
        this.genero = cliente.getGenero();
        this.edad = cliente.getEdad();
        this.contrasenia = cliente.getContrasenia();
    }

    private Cliente c;

    public Cliente transformarDtoACliente() {
        // convertir a Cliente
        c = new Cliente();
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
