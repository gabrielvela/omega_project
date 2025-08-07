package com.omega.count.integration.validator;


import com.omega.count.integration.exception.ClienteNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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
}