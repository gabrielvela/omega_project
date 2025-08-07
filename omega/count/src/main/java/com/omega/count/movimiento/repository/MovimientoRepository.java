package com.omega.count.movimiento.repository;

import com.omega.count.cuenta.model.Cuenta;
import com.omega.count.movimiento.model.Movimiento;
import com.omega.count.movimiento.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaId(Long cuentaId);

    boolean existsByFechaAndTipoMovimientoAndValorAndCuenta(Date fecha, TipoMovimiento tipo, BigDecimal valor, Cuenta cuenta);

}
