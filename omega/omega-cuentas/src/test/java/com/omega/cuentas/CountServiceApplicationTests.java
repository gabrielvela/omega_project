package com.omega.cuentas;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.movimiento.dto.MovimientoRequestDTO;
import com.omega.cuentas.movimiento.model.TipoMovimiento;
import com.omega.cuentas.movimiento.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CountServiceApplicationTests {

    @Autowired
    private MovimientoService servicio;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    @Transactional
    @Rollback
    void noDebePermitirMovimientoSiCuentaInactiva() {
        // Arrange: crear cuenta inactiva
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("123456");
        cuenta.setTipoCuenta(TipoCuenta.AHORROS);
        cuenta.setSaldoInicial(new BigDecimal("100.00"));
        cuenta.setSaldoDisponible(cuenta.getSaldoInicial());
        cuenta.setEstado(false); // cuenta inactiva
        cuenta.setClienteId(1L);

        cuenta = cuentaRepository.save(cuenta);

        // Act: preparar DTO de movimiento
        MovimientoRequestDTO mov = new MovimientoRequestDTO();
        mov.setIdCuenta(cuenta.getId());
        mov.setNumeroCuenta(cuenta.getNumeroCuenta());
        mov.setTipoMovimiento(TipoMovimiento.RETIRO);
        mov.setValor(new BigDecimal("12.50"));

        // Assert: debe lanzar IllegalStateException
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            servicio.registrarMovimiento(mov);
        });

        assertEquals("La cuenta no está activa para realizar operaciones.", ex.getMessage());
    }
}
