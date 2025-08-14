package com.omega.cuentas;

import com.omega.cuentas.cuenta.dto.CuentaCreateDTO;
import com.omega.cuentas.cuenta.dto.CuentaDTO;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CuentaServiceTest {

    @Autowired
    private CuentaService cuentaService;

    @MockitoBean
    private ClienteValidatorService clienteValidatorService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    void guardarCuentaConClienteExistente() {
        Long clienteId = 100L;

        Mockito.doNothing().when(clienteValidatorService).validarExistenciaCliente(clienteId);

        CuentaCreateDTO cuentaDTO = new CuentaCreateDTO();
        cuentaDTO.setNumeroCuenta("9876543210");
        cuentaDTO.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuentaDTO.setSaldoInicial(BigDecimal.valueOf(5000));
        cuentaDTO.setCliente(new Cliente(clienteId)); // o clienteDTO si tu DTO lo requiere

        Cuenta cuentaGuardada = cuentaService.crearCuenta(cuentaDTO);

        assertNotNull(cuentaGuardada.getId());
        assertEquals(clienteId, cuentaGuardada.getClienteId());
    }

    @Test
    void guardarCuentaConClienteInexistenteDebeFallar() {
        Long clienteId = 999L;

        Mockito.doThrow(new ClienteNoEncontradoException(clienteId))
                .when(clienteValidatorService).validarExistenciaCliente(clienteId);

        CuentaCreateDTO cuentaDTO = new CuentaCreateDTO();
        cuentaDTO.setNumeroCuenta("1234567890");
        cuentaDTO.setTipoCuenta(TipoCuenta.AHORROS);
        cuentaDTO.setSaldoInicial(BigDecimal.valueOf(3000));
        cuentaDTO.setCliente(new Cliente(clienteId)); 

        assertThrows(ClienteNoEncontradoException.class, () -> {
            cuentaService.crearCuenta(cuentaDTO);
        });
    }
}
