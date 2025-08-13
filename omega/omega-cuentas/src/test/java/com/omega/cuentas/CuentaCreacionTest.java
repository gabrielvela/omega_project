package com.omega.cuentas;

import com.omega.cuentas.cuenta.dto.CuentaCreateDTO;
import com.omega.cuentas.cuenta.dto.CuentaDTO;
import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.cuenta.service.CuentaService;
import com.omega.cuentas.integration.dto.Cliente;
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
    public void crearCuentaCorrienteParaAndresVela() {
        // Paso 1: Obtener cliente por nombre
        String nombreCliente = "Andrés Vela";
        Cliente cliente = clienteValidatorService.obtenerClientePorNombre(nombreCliente);
        Assertions.assertNotNull(cliente, "El cliente no debe ser nulo");

        // Paso 2: Crear la cuenta
        CuentaCreateDTO cuentaRequest = new CuentaCreateDTO();
        cuentaRequest.setNumeroCuenta("585545");
        cuentaRequest.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuentaRequest.setSaldoInicial(new BigDecimal("1000.0"));
        cuentaRequest.setCliente(cliente); // ✅ si usas Cliente como objeto

        CuentaCreateDTO  cuentaCreadaDTO = cuentaService.crearCuenta(cuentaRequest);
        Assertions.assertNotNull(cuentaCreadaDTO, "La cuenta creada no debe ser nula");

        // Paso 3: Validar que la cuenta fue creada en la base
        Optional<Cuenta> cuentaCreada = cuentaRepository.findByNumeroCuenta("585545");
        Assertions.assertTrue(cuentaCreada.isPresent(), "La cuenta debe existir");

        Assertions.assertEquals(cliente.getClienteId(), cuentaCreada.get().getClienteId(), "El clienteId debe coincidir");
        Assertions.assertEquals(TipoCuenta.CORRIENTE, cuentaCreada.get().getTipoCuenta(), "El tipo debe ser CORRIENTE");

        BigDecimal expected = new BigDecimal("1000.0").setScale(2);
        BigDecimal actual = cuentaCreada.get().getSaldoInicial().setScale(2);
        Assertions.assertEquals(expected, actual, "El saldo inicial debe ser 1000");
    }
}
