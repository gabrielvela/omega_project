package com.omega.cuentas;

import com.omega.cuentas.cuenta.dto.CuentaCreateDTO;
import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.cuenta.service.CuentaService;
import com.omega.cuentas.integration.dto.Cliente;
import com.omega.cuentas.integration.validator.ClienteValidatorService;
import com.omega.cuentas.movimiento.repository.MovimientoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@SpringBootTest
public class CuentaCreacionTest {

    @Autowired
    private ClienteValidatorService clienteValidatorService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository; // ✅ necesario para limpiar primero

    @BeforeEach
    public void limpiarDatos() {
        // ✅ Primero eliminamos los movimientos para evitar violación de clave foránea
        movimientoRepository.deleteAllInBatch();
        cuentaRepository.deleteAllInBatch();
    }

    @Test
    public void crearCuentaCorrienteParaJoseLema() {
        // Paso 1: Obtener cliente por nombre
        String nombreCliente = "Jose Lema";
        Cliente cliente = clienteValidatorService.obtenerClientePorNombre(nombreCliente);
        Assertions.assertNotNull(cliente, "El cliente no debe ser nulo");

        // Paso 2: Crear la cuenta
        CuentaCreateDTO cuentaRequest = new CuentaCreateDTO();
        cuentaRequest.setNumeroCuenta("585560");
        cuentaRequest.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuentaRequest.setSaldoInicial(new BigDecimal("1000.0"));
        cuentaRequest.setCliente(cliente);

        Cuenta cuenta = cuentaService.crearCuenta(cuentaRequest);
        Assertions.assertNotNull(cuenta, "La cuenta creada no debe ser nula");

        // Paso 3: Validar persistencia
        Optional<Cuenta> cuentaCreada = cuentaRepository.findByNumeroCuenta("585560");
        Assertions.assertTrue(cuentaCreada.isPresent(), "La cuenta debe existir");

        Cuenta cuentaPersistida = cuentaCreada.get();

        Assertions.assertEquals(cliente.getClienteId(), cuentaPersistida.getClienteId(), "El clienteId debe coincidir");
        Assertions.assertEquals(TipoCuenta.CORRIENTE, cuentaPersistida.getTipoCuenta(), "El tipo debe ser CORRIENTE");

        BigDecimal expected = new BigDecimal("1000.0").setScale(2);
        BigDecimal actual = cuentaPersistida.getSaldoInicial().setScale(2);
        Assertions.assertEquals(0, expected.compareTo(actual), "El saldo inicial debe ser 1000");

        Assertions.assertTrue(cuentaPersistida.getEstado(), "La cuenta debe estar activa por defecto");
    }
}