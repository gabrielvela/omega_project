package com.omega.cuentas.integration.validator;

import com.omega.cuentas.integration.dto.Cliente;
import com.omega.cuentas.integration.exception.ClienteNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ClienteValidatorService {

    private final RestTemplate restTemplate;

    @Value("${ws.cliente.path}")
    private String urlClientService;

    @Value("${ws.cliente.path.buscar.nombre}")
    private String pathBuscarClienteNombre;
    
    @Value("${ws.cliente.path.buscar.identificacion}")
    private String pathBuscarClienteIdentificacion;
    
    @Value("${ws.cliente.path.buscar.id}")
    private String pathBuscarClienteId;

    @Autowired
    public ClienteValidatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validarExistenciaCliente(Long clienteId) {
        String endpoint = urlClientService + "/" + clienteId;
        System.out.println("🔍 Validando existencia de cliente en: " + endpoint);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.GET,
                    null,
                    Void.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ClienteNoEncontradoException(clienteId);
            }

        } catch (HttpClientErrorException.NotFound e) {
            throw new ClienteNoEncontradoException(clienteId);
        } catch (RestClientException e) {
            throw new RuntimeException("❌ Error al conectar con el servicio de clientes: " + e.getMessage());
        }
    }

    public Cliente obtenerClientePorId(Long clienteId) {
        String url = pathBuscarClienteId + clienteId;
        ResponseEntity<Cliente> response = restTemplate.getForEntity(url, Cliente.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("No se pudo obtener el nombre del cliente con ID: " + clienteId);
        }
    }

    public Cliente obtenerClientePorIdentificacion(String identificacion) {
        String url = pathBuscarClienteIdentificacion + identificacion;
        ResponseEntity<Cliente> response = restTemplate.getForEntity(url, Cliente.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("No se pudo obtener el nombre del cliente con identificacion: " + identificacion);
        }
    }

    public Cliente obtenerClientePorNombre(String nombre) {
        String url = pathBuscarClienteNombre + nombre;
        ResponseEntity<Cliente> response = restTemplate.getForEntity(url, Cliente.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("No se pudo obtener el nombre del cliente con nombre: " + nombre);
        }
    }

    public Long obtenerClienteIdPorNombre(String nombre) {
        String url = pathBuscarClienteNombre + nombre;
        ResponseEntity<Cliente> response = restTemplate.getForEntity(url, Cliente.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getClienteId();
        } else {
            throw new RuntimeException("No se pudo obtener el cliente por nombre: " + nombre);
        }
    }

    public String obtenerNombrePorId(Long clienteId) {
        String url = urlClientService + clienteId;
        ResponseEntity<Cliente> response = restTemplate.getForEntity(url, Cliente.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getNombre();
        } else {
            throw new RuntimeException("No se pudo obtener el nombre del cliente con ID: " + clienteId);
        }
    }

}
