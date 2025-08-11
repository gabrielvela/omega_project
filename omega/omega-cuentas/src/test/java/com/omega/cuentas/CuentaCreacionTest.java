package com.omega.cuentas;

import com.omega.cuentas.cuenta.dto.CuentaDTO;
import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.cuenta.service.CuentaService;
import com.omega.cuentas.integration.validator.ClienteValidatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaCreacionTest {

    @Autowired
    private ClienteValidatorService clienteValidatorService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @BeforeEach
    public void limpiarDatos() {
        cuentaRepository.deleteAll();
    }

    @Test
    public void crearCuentaCorrienteParaJoseLema() {
        // Paso 1: Obtener clienteId por nombre
        String nombreCliente = "Andrés Vela";
        Long clienteId = clienteValidatorService.obtenerClienteIdPorNombre(nombreCliente);
        Assertions.assertNotNull(clienteId, "El clienteId no debe ser nulo");

        // Paso 2: Crear la cuenta
        CuentaDTO cuentaRequest = new CuentaDTO();
        cuentaRequest.setNumeroCuenta("585545");
        cuentaRequest.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuentaRequest.setSaldoInicial(new BigDecimal("1000.0"));
        cuentaRequest.setEstado(true);
        cuentaRequest.setClienteId(clienteId);

        cuentaService.crear(cuentaRequest);

        // Paso 3: Validar que la cuenta fue creada
        Optional<Cuenta> cuentaCreada = cuentaRepository.findByNumeroCuenta("585545");
        Assertions.assertTrue(cuentaCreada.isPresent(), "La cuenta debe existir");
        Assertions.assertEquals(clienteId, cuentaCreada.get().getClienteId(), "El clienteId debe coincidir");
        Assertions.assertEquals("Corriente", cuentaCreada.get().getTipoCuenta(), "El tipo debe ser Corriente");
        BigDecimal expected = new BigDecimal("1000.0").setScale(2);
        BigDecimal actual = cuentaCreada.get().getSaldoInicial().setScale(2);

        Assertions.assertEquals(expected, actual, "El saldo inicial debe ser 1000");

    }
}
