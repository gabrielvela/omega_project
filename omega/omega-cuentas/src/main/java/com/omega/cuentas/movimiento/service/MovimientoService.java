package com.omega.cuentas.movimiento.service;

import com.omega.cuentas.movimiento.exception.SaldoInsuficienteException;
import com.omega.cuentas.movimiento.exception.CuentaInexistenteException;
import com.omega.cuentas.cuenta.dto.CuentaDTO;
import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.cuenta.service.CuentaService;
import com.omega.cuentas.movimiento.dto.MovimientoDTO;
import com.omega.cuentas.movimiento.dto.MovimientoRequestDTO;
import com.omega.cuentas.movimiento.model.Movimiento;
import com.omega.cuentas.movimiento.model.TipoMovimiento;
import com.omega.cuentas.movimiento.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MovimientoRepository movimientoRepository;

//    public MovimientoService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
//        this.cuentaRepository = cuentaRepository;
//        this.movimientoRepository = movimientoRepository;
//    }
    public List<MovimientoDTO> obtenerTodos() {
        return movimientoRepository.findAll()
                .stream()
                .map(MovimientoDTO::new)
                .collect(Collectors.toList());
    }

    private LocalDateTime fechaActual;
    private Date fechaTruncada;

    @Transactional
    public Movimiento registrarMovimiento(MovimientoRequestDTO dto) {
        Cuenta cuenta = cuentaService.obtenerCuentaCriterios(dto.getIdCuenta(), dto.getNumeroCuenta());

        if (!cuenta.estaActiva()) {
            throw new IllegalStateException("La cuenta no está activa para realizar operaciones.");
        }

//        if (dto.getValor() == null || dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("El valor debe ser mayor a cero");
//        }
        if (dto.getTipoMovimiento() == null) {
            throw new IllegalArgumentException("El tipo de movimiento es obligatorio");
        }

        // Truncar fecha para evitar duplicados
        fechaActual = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        fechaTruncada = Date.from(fechaActual.atZone(ZoneId.systemDefault()).toInstant());

        if (movimientoRepository.existsByFechaAndTipoMovimientoAndValorAndCuenta(
                fechaTruncada, dto.getTipoMovimiento(), dto.getValor(), cuenta)) {
            throw new IllegalArgumentException("Movimiento duplicado");
        }

        // Normalizar valor
        BigDecimal valor = dto.getValor();

        if (valor.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Valor no puede ser cero");
        }

        if (dto.getTipoMovimiento() == TipoMovimiento.DEPOSITO && valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor debe ser positivo");
        }
//        else {
//            nuevoSaldo = cuenta.getSaldoDisponible().add(valor);
//        }

        if (dto.getTipoMovimiento() == TipoMovimiento.RETIRO && valor.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Valor debe ser negativo");
        }
//        else {
//            nuevoSaldo = cuenta.getSaldoDisponible().add(valor);
//        }

        BigDecimal nuevoSaldo = cuenta.getSaldoDisponible().add(valor);

        if (dto.getTipoMovimiento() == TipoMovimiento.RETIRO && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }

        Movimiento movimiento = new Movimiento(null, fechaTruncada, dto.getTipoMovimiento(), valor, nuevoSaldo, cuenta);

        cuenta.setSaldoDisponible(nuevoSaldo);
        cuentaRepository.save(cuenta);

        return movimientoRepository.save(movimiento);
    }

    public Movimiento obtenerPorId(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Movimiento actualizarMovimiento(Long id, TipoMovimiento tipo, BigDecimal valor)
            throws SaldoInsuficienteException, CuentaInexistenteException {

        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new CuentaInexistenteException("Cuenta no encontrada"));

        if (!cuenta.estaActiva()) {
            throw new IllegalStateException("La cuenta está inactiva");
        }

        // Validación de duplicado
        if (movimientoRepository.existsByFechaAndTipoMovimientoAndValorAndCuenta(
                movimiento.getFecha(), tipo, valor, cuenta)) {
            throw new IllegalArgumentException("Movimiento duplicado");
        }

        // Validación de signo
        if (tipo == TipoMovimiento.DEPOSITO && valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor debe ser positivo");
        }

        if (tipo == TipoMovimiento.RETIRO && valor.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Valor debe ser negativo");
        }

        // Revertir el saldo anterior
        BigDecimal saldoActual = cuenta.getSaldoDisponible().subtract(movimiento.getValor());

        // Aplicar el nuevo valor
        BigDecimal nuevoSaldo = saldoActual.add(valor);

        if (tipo == TipoMovimiento.RETIRO && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }

        // Actualizar movimiento
        movimiento.setTipoMovimiento(tipo);
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);

        // Actualizar cuenta
        cuenta.setSaldoDisponible(nuevoSaldo);
        cuentaRepository.save(cuenta);

        return movimientoRepository.save(movimiento);
    }

//    @Transactional
//    public Movimiento revertirMovimiento(Long idMovimientoOriginal) {
//        Movimiento original = movimientoRepository.findById(idMovimientoOriginal)
//                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
//
//        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta())
//                .orElseThrow(() -> new CuentaInexistenteException("Cuenta no encontrada"));
//
//        // Crear movimiento reverso
//        Movimiento reverso = new Movimiento();
//        reverso.setCuenta(cuenta);
//        reverso.setFecha(new Date());
//        reverso.setValor(original.getValor());
//        reverso.setTipoMovimiento(original.getTipoMovimiento() == TipoMovimiento.DEPOSITO
//                ? TipoMovimiento.RETIRO
//                : TipoMovimiento.DEPOSITO);
//        reverso.setDescripcion("Reverso de movimiento ID " + original.getId());
//
//        // Actualizar saldo
//        BigDecimal nuevoSaldo = reverso.getTipoMovimiento() == TipoMovimiento.DEPOSITO
//                ? cuenta.getSaldoDisponible().add(reverso.getValor())
//                : cuenta.getSaldoDisponible().subtract(reverso.getValor());
//
//        cuenta.setSaldoDisponible(nuevoSaldo);
//        reverso.setSaldoDisponible(nuevoSaldo);
//
//        // Persistir
//        movimientoRepository.save(reverso);
//        cuentaRepository.save(cuenta);
//
//        return reverso;
//    }
    @Transactional
    public boolean eliminarMovimiento(Long id) {
        Optional<Movimiento> movimientoOpt = movimientoRepository.findById(id);

        if (movimientoOpt.isPresent()) {
            Movimiento movimiento = movimientoOpt.get();
            Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                    .orElseThrow(() -> new CuentaInexistenteException("Cuenta no encontrada"));

            // Revertir el efecto del movimiento en el saldo
            if (movimiento.getTipoMovimiento() == TipoMovimiento.DEPOSITO) {
                cuenta.setSaldoDisponible(cuenta.getSaldoDisponible().subtract(movimiento.getValor().abs())); // ✅ Correcto
            } else if (movimiento.getTipoMovimiento() == TipoMovimiento.RETIRO) {
                cuenta.setSaldoDisponible(cuenta.getSaldoDisponible().add(movimiento.getValor().abs()));
            }

            // Guardar el nuevo saldo
            cuentaRepository.save(cuenta);

            // Eliminar el movimiento
            movimientoRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
