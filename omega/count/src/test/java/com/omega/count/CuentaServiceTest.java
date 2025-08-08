package com.omega.count;

import com.omega.count.cuenta.model.Cuenta;
import com.omega.count.cuenta.repository.CuentaRepository;
import com.omega.count.cuenta.service.CuentaService;
import com.omega.count.integration.exception.ClienteNoEncontradoException;
import com.omega.count.integration.validator.ClienteValidatorService;
import com.omega.count.movimiento.model.TipoMovimiento;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("9876543210");
        cuenta.setTipoCuenta("CORRIENTE");
        cuenta.setSaldoInicial(BigDecimal.valueOf(5000));
        cuenta.setClienteId(clienteId);
        cuenta.setEstado("ACTIVA");

        Cuenta cuentaGuardada = cuentaService.crear(Cuenta.convertirCuentaADTO(cuenta));

        assertNotNull(cuentaGuardada.getId());
        assertEquals(clienteId, cuentaGuardada.getClienteId());
    }

    @Test
    void guardarCuentaConClienteInexistenteDebeFallar() {
        Long clienteId = 999L;

        Mockito.doThrow(new ClienteNoEncontradoException(clienteId))
                .when(clienteValidatorService).validarExistenciaCliente(clienteId);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipoCuenta("AHORROS");
        cuenta.setSaldoInicial(BigDecimal.valueOf(3000));
        cuenta.setClienteId(clienteId);
        cuenta.setEstado("ACTIVA");

        assertThrows(ClienteNoEncontradoException.class, () -> {
            cuentaService.crear(Cuenta.convertirCuentaADTO(cuenta));
        });
    }
}