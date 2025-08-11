package com.omega.clientes.service;

import com.omega.clientes.model.Cliente;
import com.omega.clientes.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.util.ReflectionUtils;


import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente crear(Cliente cliente) {
        // Validación personalizada
        if (clienteRepository.existsByIdentificacion(cliente.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un cliente con esa identificación.");
        }

        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setGenero(clienteActualizado.getGenero());
            cliente.setEdad(clienteActualizado.getEdad());
            cliente.setIdentificacion(clienteActualizado.getIdentificacion());
            cliente.setDireccion(clienteActualizado.getDireccion());
            cliente.setTelefono(clienteActualizado.getTelefono());
            cliente.setContrasenia(clienteActualizado.getContrasenia());
            cliente.setEstado(clienteActualizado.isEstado());
            return clienteRepository.save(cliente);
        }).orElse(null);
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente actualizarParcialmente(Long id, Map<String, Object> camposActualizados) {
        return clienteRepository.findById(id).map(cliente -> {
            camposActualizados.forEach((key, value) -> {
                Field campo = ReflectionUtils.findField(Cliente.class, key);
                if (campo != null) {
                    campo.setAccessible(true);
                    ReflectionUtils.setField(campo, cliente, value);
                }
            });
            return clienteRepository.save(cliente);
        }).orElse(null);
    }
}