package com.omega.user;

import com.omega.user.model.Cliente;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

//Pruebas unitarias del cliente
public class ClienteTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void clienteValidoNoDebeTenerViolaciones() {
        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setContrasenia("segura123");
        cliente.setEstado(true);
        cliente.setNombre("Gabriel");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Av. Principal 123");
        cliente.setTelefono("0999999999");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        assertThat(violations).isEmpty();
    }

    @Test
    void clienteConNombreVacioDebeGenerarViolacion() {
        Cliente cliente = new Cliente();
        cliente.setNombre(""); // Violación de @NotBlank
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Av. Principal 123");
        cliente.setTelefono("0999999999");
        cliente.setContrasenia("segura123");
        cliente.setEstado(true);

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nombre"));
    }

    @Test
    void clienteConEdadNegativaDebeGenerarViolacion() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Gabriel");
        cliente.setGenero("Masculino");
        cliente.setEdad(-5); // ❌ Violación esperada
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Av. Principal 123");
        cliente.setTelefono("0999999999");
        cliente.setContrasenia("segura123");
        cliente.setEstado(true);

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("edad"));
    }
}