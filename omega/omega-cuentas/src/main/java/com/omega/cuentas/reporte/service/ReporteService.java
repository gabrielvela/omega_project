package com.omega.cuentas.reporte.service;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.integration.validator.ClienteValidatorService;
import com.omega.cuentas.movimiento.model.Movimiento;
import com.omega.cuentas.movimiento.repository.MovimientoRepository;
import com.omega.cuentas.reporte.dto.CuentaResumenDTO;
import com.omega.cuentas.reporte.dto.EstadoCuentaResponseDTO;
import com.omega.cuentas.reporte.dto.MovimientoReporteDTO;
import com.omega.cuentas.reporte.dto.MovimientoRequestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteValidatorService clienteValidator;

    public ReporteService(CuentaRepository cuentaRepository,
            MovimientoRepository movimientoRepository,
            ClienteValidatorService clienteValidator) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.clienteValidator = clienteValidator;
    }

//    public EstadoCuentaResponseDTO generarEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
//        clienteValidator.validarExistenciaCliente(clienteId);
//
//        // Convertir LocalDate a java.util.Date
//        Date inicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        Date finDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
//
//        List<CuentaResumenDTO> resumenes = cuentas.stream().map(cuenta -> {
//            List<Movimiento> movimientos = movimientoRepository
//                    .findByCuentaIdAndFechaBetween(cuenta.getId(), inicioDate, finDate);
//
//            List<MovimientoRequestDTO> movimientoDTOs = movimientos.stream().map(mov -> {
//                MovimientoRequestDTO dto = new MovimientoRequestDTO();
//                dto.setFecha(mov.getFecha());
//                dto.setTipo(mov.getTipoMovimiento());
//                dto.setValor(mov.getValor());
//                return dto;
//            }).collect(Collectors.toList());
//
//            CuentaResumenDTO resumen = new CuentaResumenDTO();
//            resumen.setNumeroCuenta(cuenta.getNumeroCuenta());
//            resumen.setSaldoInicial(cuenta.getSaldoInicial());
//            resumen.setSaldoDisponible(cuenta.getSaldoDisponible());
//            resumen.setMovimientos(movimientoDTOs);
//
//            return resumen;
//        }).collect(Collectors.toList());
//
//        EstadoCuentaResponseDTO response = new EstadoCuentaResponseDTO();
//        response.setClienteId(clienteId);
//        response.setCuentas(resumenes);
//
//        return response;
//    }
    @Transactional(readOnly = true)
    public List<MovimientoReporteDTO> generarEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        clienteValidator.validarExistenciaCliente(clienteId);

        String nombreCliente = clienteValidator.obtenerNombrePorId(clienteId);

        Date inicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        List<MovimientoReporteDTO> reporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository
                    .findByCuentaIdAndFechaBetween(cuenta.getId(), inicioDate, finDate);

            for (Movimiento mov : movimientos) {
                MovimientoReporteDTO dto = new MovimientoReporteDTO();
                dto.setFecha(mov.getFecha());
                dto.setCliente(nombreCliente);
                dto.setNumeroCuenta(cuenta.getNumeroCuenta());
                dto.setTipoCuenta(cuenta.getTipoCuenta());
                dto.setSaldoInicial(cuenta.getSaldoInicial());
                dto.setEstado(cuenta.getEstado());
                dto.setMovimiento(mov.getValor());
                dto.setSaldo(mov.getSaldo());

                reporte.add(dto);
            }
        }

        return reporte;
    }

    @Transactional(readOnly = true)
    public List<MovimientoReporteDTO> generarEstadoCuentaPorNombre(String nombreCliente, LocalDate fechaInicio, LocalDate fechaFin) {
        Long clienteId = clienteValidator.obtenerClienteIdPorNombre(nombreCliente);

        Date inicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        List<MovimientoReporteDTO> reporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository
                    .findByCuentaIdAndFechaBetween(cuenta.getId(), inicioDate, finDate);

            for (Movimiento mov : movimientos) {
                MovimientoReporteDTO dto = new MovimientoReporteDTO();
                dto.setFecha(mov.getFecha());
                dto.setCliente(nombreCliente);
                dto.setNumeroCuenta(cuenta.getNumeroCuenta());
                dto.setTipoCuenta(cuenta.getTipoCuenta());
                dto.setSaldoInicial(cuenta.getSaldoInicial());
                dto.setEstado(cuenta.getEstado());
                dto.setMovimiento(mov.getValor());
                dto.setSaldo(mov.getSaldo());

                reporte.add(dto);
            }
        }

        return reporte;
    }

}
