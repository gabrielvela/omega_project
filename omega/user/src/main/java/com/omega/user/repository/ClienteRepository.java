package com.omega.user.repository;

import com.omega.user.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método personalizado
    Cliente findByIdentificacion(String identificacion);

    boolean existsByIdentificacion(String identificacion);

}
