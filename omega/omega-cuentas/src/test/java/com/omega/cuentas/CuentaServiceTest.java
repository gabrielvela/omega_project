package com.omega.cuentas;

import com.omega.cuentas.cuenta.dto.CuentaCreateDTO;
import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.cuenta.service.CuentaService;
import com.omega.cuentas.integration.dto.Cliente;
import com.omega.cuentas.integration.exception.ClienteNoEncontradoException;
import com.omega.cuentas.integration.validator.ClienteValidatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class CuentaServiceTest {

    @Autowired
    private CuentaService cuentaService;

    @MockBean
    private ClienteValidatorService clienteValidatorService;

    /*@Test
    void guardarCuentaConClienteExistente() {
        // Arrange
        Long clienteId = 100L;

        Cliente clienteMock = new Cliente();
        clienteMock.setClienteId(clienteId);
        clienteMock.setNombre("Jose Lema");
        clienteMock.setIdentificacion("1717171717");
        clienteMock.setDireccion("Quito");
        clienteMock.setTelefono("0999999999");
        clienteMock.setEstado(true);

        // Simular validación exitosa
        Mockito.doNothing().when(clienteValidatorService).validarExistenciaCliente(clienteId);

        CuentaCreateDTO cuentaDTO = new CuentaCreateDTO();
        cuentaDTO.setNumeroCuenta("9876543210");
        cuentaDTO.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuentaDTO.setSaldoInicial(BigDecimal.valueOf(5000));
        cuentaDTO.setCliente(clienteMock); // Cliente simulado

        // Act
        Cuenta cuentaGuardada = cuentaService.crearCuenta(cuentaDTO);

        // Assert
        assertNotNull(cuentaGuardada.getId(), "La cuenta debe tener un ID generado");
        assertEquals(clienteId, cuentaGuardada.getClienteId(), "El clienteId debe coincidir");
        assertEquals("9876543210", cuentaGuardada.getNumeroCuenta());
        assertEquals(TipoCuenta.CORRIENTE, cuentaGuardada.getTipoCuenta());
        assertEquals(0, cuentaGuardada.getSaldoInicial().compareTo(BigDecimal.valueOf(5000)));
        assertTrue(cuentaGuardada.getEstado(), "La cuenta debe estar activa por defecto");
    }*/

    /*@Test
    void guardarCuentaConClienteInexistenteDebeFallar() {
        // Arrange
        Long clienteId = 999L;

        Cliente clienteFalso = new Cliente();
        clienteFalso.setClienteId(clienteId);

        // Simular validación fallida
        Mockito.doThrow(new ClienteNoEncontradoException(clienteId))
               .when(clienteValidatorService).validarExistenciaCliente(clienteId);

        CuentaCreateDTO cuentaDTO = new CuentaCreateDTO();
        cuentaDTO.setNumeroCuenta("1234567890");
        cuentaDTO.setTipoCuenta(TipoCuenta.AHORROS);
        cuentaDTO.setSaldoInicial(BigDecimal.valueOf(3000));
        cuentaDTO.setCliente(clienteFalso);

        // Act & Assert
        assertThrows(ClienteNoEncontradoException.class, () -> {
            cuentaService.crearCuenta(cuentaDTO);
        }, "Debe lanzar ClienteNoEncontradoException si el cliente no existe");
    }*/
}