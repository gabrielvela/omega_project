package com.omega.cuentas.integration.validator;


import com.omega.cuentas.integration.dto.ClienteDTO;
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

    @Value("${servicio.usuario.url}")
    private String urlClientService;


    @Value("${servicio.usuario.url.buscar.nombre}")
    private String pathBuscarClienteNombre;

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


    public Long obtenerClienteIdPorNombre(String nombre) {
        String url = pathBuscarClienteNombre + nombre;
        ResponseEntity<ClienteDTO> response = restTemplate.getForEntity(url, ClienteDTO.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getClienteId();
        } else {
            throw new RuntimeException("No se pudo obtener el cliente por nombre: " + nombre);
        }
    }

    public String obtenerNombrePorId(Long clienteId) {
        String url = "http://localhost:8080/clientes/" + clienteId;
        ResponseEntity<ClienteDTO> response = restTemplate.getForEntity(url, ClienteDTO.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getNombre();
        } else {
            throw new RuntimeException("No se pudo obtener el nombre del cliente con ID: " + clienteId);
        }
    }


}