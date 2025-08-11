package com.omega.cuentas;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.model.TipoCuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.movimiento.model.TipoMovimiento;
import com.omega.cuentas.movimiento.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CountServiceApplicationTests {

	@Autowired
	private MovimientoService servicio;

	@Autowired
	private CuentaRepository cuentaRepository;

	@Transactional
	@Rollback
	@Test
	void noDebePermitirMovimientoSiCuentaInactiva() {
		// Crear y guardar la cuenta inactiva
		Cuenta cuenta = new Cuenta();
		cuenta.setNumeroCuenta("123456");
		cuenta.setTipoCuenta(TipoCuenta.AHORROS);
		cuenta.setSaldoInicial(new BigDecimal("100.00"));
		cuenta.setSaldoDisponible(cuenta.getSaldoInicial());
		cuenta.setEstado(false);
		cuenta.setClienteId(1L);

		cuenta = cuentaRepository.save(cuenta); // ahora tiene ID

		// Ejecutar y validar
		Long cuentaId = cuenta.getId();

		assertThrows(IllegalStateException.class, () -> {
			servicio.registrarMovimiento(cuentaId, TipoMovimiento.DEBITO, new BigDecimal("12.50"));
		});
	}
}
