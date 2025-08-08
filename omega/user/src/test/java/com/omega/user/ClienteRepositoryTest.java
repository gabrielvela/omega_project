package com.omega.user;

import com.omega.user.model.Cliente;
import com.omega.user.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void noDebePermitirIdentificacionDuplicada() {
        // Primer cliente
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Gabriel");
        cliente1.setGenero("Masculino");
        cliente1.setEdad(30);
        cliente1.setIdentificacion("1234567890");
        cliente1.setDireccion("Av. Principal 123");
        cliente1.setTelefono("0999999999");
        cliente1.setContrasenia("segura123");
        cliente1.setEstado(true);

        clienteRepository.save(cliente1);

        // Segundo cliente con misma identificación
        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Otro");
        cliente2.setGenero("Femenino");
        cliente2.setEdad(25);
        cliente2.setIdentificacion("1234567890"); // ❌ Duplicado
        cliente2.setDireccion("Av. Secundaria 456");
        cliente2.setTelefono("0888888888");
        cliente2.setContrasenia("clave456");
        cliente2.setEstado(true);

        // Act & Assert
        assertThatThrownBy(() -> clienteRepository.saveAndFlush(cliente2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
