package com.omega.cuentas.movimiento.repository;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.movimiento.model.Movimiento;
import com.omega.cuentas.movimiento.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaId(Long cuentaId);

    boolean existsByFechaAndTipoMovimientoAndValorAndCuenta(Date fecha, TipoMovimiento tipo, BigDecimal valor, Cuenta cuenta);

    //    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate inicio, LocalDate fin);
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, Date fechaInicio, Date fechaFin);

    boolean existsByCuentaId(Long cuentaId);

}
