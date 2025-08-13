package com.omega.clientes.service;

import com.omega.clientes.dto.ClienteCrearDTO;
import com.omega.clientes.dto.ClienteDTO;
import com.omega.clientes.model.Cliente;
import com.omega.clientes.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {//Clase de negocio

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Lista todos los clientes registrados en db en formato DTO
     *
     * @return List ClienteDTO
     */
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Permite crear un nuevo cliente
     *
     * @param clienteDTO
     * @return ClienteDTO
     */
    public ClienteDTO crear(ClienteCrearDTO clienteDTO) {
        if (clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un cliente con esa identificación");
        }
        if (clienteRepository.existsByNombre(clienteDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese nombre");
        }

        Cliente cliente = clienteDTO.transformarDtoACliente();
        Cliente guardado = clienteRepository.save(cliente);
        return new ClienteDTO(guardado);
    }

    /**
     * Permite buscar el cliente por su nombre
     *
     * @param nombreCliente
     * @return ClienteDTO
     */
    public ClienteDTO buscarPorNombre(String nombreCliente) {
        Cliente cliente = clienteRepository.findByNombre(nombreCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        return new ClienteDTO(cliente);
    }

    /**
     * Permite buscar el cliente por su identificación
     *
     * @param identificacion
     * @return ClienteDTO
     */
    public ClienteDTO buscarPorIdentificacion(String identificacion) {
        Cliente cliente = clienteRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        return new ClienteDTO(cliente);
    }

    /**
     * Permite buscar el cliente por su clienteId, identificacion o nombre
     *
     * @param clienteId
     * @param identificacion
     * @param nombre
     * @return ClienteDTO
     */
    public ClienteDTO buscarCliente(Long clienteId, String identificacion) {
        if (clienteId == null && identificacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe proporcionar al menos un criterio de búsqueda");
        }

        Cliente cliente;

        if (clienteId != null) {
            cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con ID " + clienteId + " no encontrado"));
        } else {
            cliente = clienteRepository.findByIdentificacion(identificacion)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con identificación " + identificacion + " no encontrado"));
        }

        return new ClienteDTO(cliente);
    }

    /**
     * Permite actualizar totalmente el cliente
     *
     * @param clienteId
     * @param identificacion
     * @param clienteDTO
     * @return ClienteDTO
     */
    public ClienteDTO actualizarCliente(Long clienteId, String identificacion, ClienteDTO clienteDTO) {
        if (clienteId == null && identificacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe proporcionar al menos un criterio de búsqueda");
        }

        Cliente cliente;

        if (clienteId != null) {
            cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con ID " + clienteId + " no encontrado"));
        } else {
            cliente = clienteRepository.findByIdentificacion(identificacion)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con identificación " + identificacion + " no encontrado"));
        }

        // Actualizar campos
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setEstado(clienteDTO.getEstado());
        cliente.setContrasenia(clienteDTO.getContrasenia());

        Cliente actualizado = clienteRepository.save(cliente);
        return new ClienteDTO(actualizado);
    }

    /**
     * Permite actualizar parcialmente el cliente con los campos recibidos
     * @param clienteId
     * @param identificacion
     * @param campos
     * @return ClienteDTO
     */
    public ClienteDTO actualizarParcialmenteCliente(Long clienteId, String identificacion, Map<String, Object> campos) {
        if ((clienteId == null && identificacion == null) || campos == null || campos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe proporcionar un criterio de búsqueda y al menos un campo a actualizar");
        }

        Cliente cliente;

        if (clienteId != null) {
            cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con ID " + clienteId + " no encontrado"));
        } else {
            cliente = clienteRepository.findByIdentificacion(identificacion)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con identificación " + identificacion + " no encontrado"));
        }

        campos.forEach((clave, valor) -> {
            Field campo;
            try {
                campo = ReflectionUtils.findField(Cliente.class, clave);
                if (campo != null) {
                    campo.setAccessible(true);
                    ReflectionUtils.setField(campo, cliente, valor);
                }
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al actualizar el campo: " + clave);
            }
        });

        Cliente actualizado = clienteRepository.save(cliente);
        return new ClienteDTO(actualizado);
    }

    /**
     * Permite eliminar el cliente por su id o identificación
     *
     * @param clienteId
     * @param identificacion
     */
    public void eliminarCliente(Long clienteId, String identificacion) {
        if (clienteId == null && identificacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe proporcionar al menos un criterio de eliminación");
        }

        Cliente cliente;

        if (clienteId != null) {
            cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con ID " + clienteId + " no encontrado"));
        } else {
            cliente = clienteRepository.findByIdentificacion(identificacion)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con identificación " + identificacion + " no encontrado"));
        }

        clienteRepository.delete(cliente);
    }

}
