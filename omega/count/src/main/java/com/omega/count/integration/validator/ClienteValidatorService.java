package com.omega.count.integration.validator;


import com.omega.count.integration.exception.ClienteNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class ClienteValidatorService {

    private final RestTemplate restTemplate;

    @Value("${servicio.usuario.url}")
    private String usuarioServiceUrl;

    public ClienteValidatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validarExistenciaCliente(Long clienteId) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    usuarioServiceUrl + "/" + clienteId,
                    HttpMethod.GET,
                    null,
                    Void.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ClienteNoEncontradoException(clienteId);
            }

        } catch (HttpClientErrorException.NotFound e) {
            throw new ClienteNoEncontradoException(clienteId);
        } catch (Exception e) {
            throw new RuntimeException("Error al validar cliente: " + e.getMessage());
        }
    }
}