package com.omega.cuentas.movimiento.service;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.movimiento.model.Movimiento;
import com.omega.cuentas.movimiento.model.TipoMovimiento;
import com.omega.cuentas.movimiento.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    @Autowired
    private final CuentaRepository cuentaRepository;
    @Autowired
    private final MovimientoRepository movimientoRepository;

    public MovimientoService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional
    public Movimiento registrarMovimientoConNumeroCuenta(String numeroCuenta, TipoMovimiento tipo, BigDecimal valor) throws SaldoInsuficienteException, CuentaInexistenteException {
        Cuenta cuenta;
        cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaInexistenteException("Cuenta no encontrada"));

        //Validación del estado de la cuenta para realizar transacciones
        if (!cuenta.estaActiva()) {
            throw new IllegalStateException("La cuenta no está activa para realizar operaciones.");
        }

        // Validación de movimiento duplicado
        if (movimientoRepository.existsByFechaAndTipoMovimientoAndValorAndCuenta(
                new Date(), tipo, valor, cuenta)) {
            throw new IllegalArgumentException("Movimiento duplicado");
        }

        BigDecimal nuevoSaldo = tipo == TipoMovimiento.RETIRO
                ? cuenta.getSaldoDisponible().subtract(valor)
                : cuenta.getSaldoDisponible().add(valor);

        //Solo el RETIRO puede causar saldo cero o menor
        if (tipo == TipoMovimiento.RETIRO && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }


        Movimiento movimiento = new Movimiento(null, new Date(), tipo, valor, nuevoSaldo, cuenta);

        cuenta.setSaldoDisponible(nuevoSaldo);
        cuentaRepository.save(cuenta);
        
        return movimientoRepository.save(movimiento);
    }
//
//    @Transactional
//    public Movimiento registrarMovimientoConNumeroCuenta(Long cuentaId, TipoMovimiento tipo, BigDecimal valor) throws SaldoInsuficienteException, CuentaInexistenteException {
//        Cuenta cuenta;
//        cuenta = cuentaRepository.findById(cuentaId)
//                .orElseThrow(() -> new CuentaInexistenteException("Cuenta no encontrada"));
//
//        //Validación del estado de la cuenta para realizar transacciones
//        if (!cuenta.estaActiva()) {
//            throw new IllegalStateException("La cuenta no está activa para realizar operaciones.");
//        }
//
//        // Validación de movimiento duplicado
//        if (movimientoRepository.existsByFechaAndTipoMovimientoAndValorAndCuenta(
//                new Date(), tipo, valor, cuenta)) {
//            throw new IllegalArgumentException("Movimiento duplicado");
//        }
//
//        BigDecimal nuevoSaldo = tipo == TipoMovimiento.RETIRO
//                ? cuenta.getSaldoDisponible().subtract(valor)
//                : cuenta.getSaldoDisponible().add(valor);
//
//        //Solo el RETIRO puede causar saldo cero o menor
//        if (tipo == TipoMovimiento.RETIRO && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
//            throw new SaldoInsuficienteException("Saldo no disponible");
//        }
//
//
//        cuenta.setSaldoDisponible(nuevoSaldo);
//        cuentaRepository.save(cuenta);
//
//        Movimiento movimiento = new Movimiento(null, new Date(), tipo, valor, nuevoSaldo, cuenta);
//        
//        return movimientoRepository.save(movimiento);
//    }


    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    public Movimiento obtenerPorId(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    public Movimiento actualizarMovimiento(Long id, TipoMovimiento tipo, BigDecimal valor) throws SaldoInsuficienteException, CuentaInexistenteException {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));

        Cuenta cuenta = movimiento.getCuenta();

        // Revertir el saldo anterior
        BigDecimal saldoRevertido = movimiento.getTipoMovimiento() == TipoMovimiento.RETIRO
                ? cuenta.getSaldoDisponible().add(movimiento.getValor())
                : cuenta.getSaldoDisponible().subtract(movimiento.getValor());

        // Aplicar nuevo movimiento
        BigDecimal nuevoSaldo = tipo == TipoMovimiento.RETIRO
                ? saldoRevertido.subtract(valor)
                : saldoRevertido.add(valor);

        if (tipo == TipoMovimiento.RETIRO && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        cuenta.setSaldoDisponible(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setTipoMovimiento(tipo);
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setFecha(new Date());

        return movimientoRepository.save(movimiento);
    }


//    @Transactional
//    public Movimiento revertirMovimiento(Long idMovimientoOriginal) {
//        Movimiento original = movimientoRepository.findById(idMovimientoOriginal)
//                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
//
//        Cuenta cuenta = original.getCuenta();
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
            Cuenta cuenta = movimiento.getCuenta();

            // Revertir el efecto del movimiento en el saldo
            if (movimiento.getTipoMovimiento() == TipoMovimiento.DEPOSITO) {
                cuenta.setSaldoDisponible(cuenta.getSaldoDisponible().subtract(movimiento.getValor())); // ✅ Correcto
            } else if (movimiento.getTipoMovimiento() == TipoMovimiento.RETIRO) {
                cuenta.setSaldoDisponible(cuenta.getSaldoDisponible().add(movimiento.getValor()));
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

