package com.omega.cuentas.reporte.service;

import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.integration.dto.Cliente;
import com.omega.cuentas.integration.validator.ClienteValidatorService;
import com.omega.cuentas.movimiento.dto.MovimientoDTO;
import com.omega.cuentas.movimiento.model.Movimiento;
import com.omega.cuentas.movimiento.repository.MovimientoRepository;
import com.omega.cuentas.reporte.dto.EstadoCuentaDTO;
import com.omega.cuentas.reporte.dto.MovimientoReporteDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional(readOnly = true)
    public List<EstadoCuentaDTO> generarEstadoCuentaAgrupado(String nombreCliente, Long idCliente, String identificacion,
            LocalDate fechaInicio, LocalDate fechaFin) {

        Cliente cli = resolverClienteId(nombreCliente, idCliente, identificacion);

        Date inicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finDate = Date.from(fechaFin.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(cli.getClienteId());
        List<EstadoCuentaDTO> reporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository
                    .findByCuentaIdAndFechaBetween(cuenta.getId(), inicioDate, finDate);

            if (movimientos.isEmpty()) {
                continue;
            }

            EstadoCuentaDTO dto = new EstadoCuentaDTO();
            dto.setNombreCliente(cli.getNombre());
            dto.setNumeroCuenta(cuenta.getNumeroCuenta());
            dto.setTipoCuenta(cuenta.getTipoCuenta());
            dto.setSaldoInicial(cuenta.getSaldoInicial());
            dto.setEstado(cuenta.getEstado());
            dto.setSaldoDisponible(cuenta.getSaldoDisponible());

            List<MovimientoDTO> movimientosDTO = movimientos.stream()
                    .map(MovimientoDTO::new)
                    .collect(Collectors.toList());

            dto.setMovimientos(movimientosDTO);
            reporte.add(dto);
        }

        return reporte;
    }

    private Cliente resolverClienteId(String nombreCliente, Long clienteId, String identificacionCliente) {
        if (clienteId == null && identificacionCliente == null && nombreCliente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe proporcionar id, identificación o nombre al criterio de búsqueda de cliente");
        }

        if (clienteId != null) {
            return clienteValidator.obtenerClientePorId(clienteId);
        } else if (identificacionCliente != null) {
            return clienteValidator.obtenerClientePorIdentificacion(identificacionCliente);
        } else if (nombreCliente != null) {
            return clienteValidator.obtenerClientePorNombre(nombreCliente);
        } else {
            throw new IllegalArgumentException("Debe proporcionar un criterio de búsqueda");
        }
    }

}
