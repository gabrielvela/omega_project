package com.omega.clientes.repository;

import com.omega.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método personalizado
    Cliente findByIdentificacion(String identificacion);

    boolean existsByIdentificacion(String identificacion);

    Optional<Cliente> findByNombre(String nombre);
}
