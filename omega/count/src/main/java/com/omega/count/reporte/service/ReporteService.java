package com.omega.count.reporte.service;

import com.omega.count.cuenta.model.Cuenta;
import com.omega.count.cuenta.repository.CuentaRepository;
import com.omega.count.integration.validator.ClienteValidatorService;
import com.omega.count.movimiento.dto.MovimientoDTO;
import com.omega.count.movimiento.model.Movimiento;
import com.omega.count.movimiento.repository.MovimientoRepository;
import com.omega.count.reporte.dto.CuentaResumenDTO;
import com.omega.count.reporte.dto.EstadoCuentaResponseDTO;
import com.omega.count.reporte.dto.MovimientoRequestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public EstadoCuentaResponseDTO generarEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        clienteValidator.validarExistenciaCliente(clienteId);

        // Convertir LocalDate a java.util.Date
        Date inicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        List<CuentaResumenDTO> resumenes = cuentas.stream().map(cuenta -> {
            List<Movimiento> movimientos = movimientoRepository
                    .findByCuentaIdAndFechaBetween(cuenta.getId(), inicioDate, finDate);

            List<MovimientoRequestDTO> movimientoDTOs = movimientos.stream().map(mov -> {
                MovimientoRequestDTO dto = new MovimientoRequestDTO();
                dto.setFecha(mov.getFecha());
                dto.setTipo(mov.getTipoMovimiento());
                dto.setValor(mov.getValor());
                return dto;
            }).collect(Collectors.toList());

            CuentaResumenDTO resumen = new CuentaResumenDTO();
            resumen.setNumeroCuenta(cuenta.getNumeroCuenta());
            resumen.setSaldoInicial(cuenta.getSaldoInicial());
            resumen.setSaldoDisponible(cuenta.getSaldoDisponible());
            resumen.setMovimientos(movimientoDTOs);

            return resumen;
        }).collect(Collectors.toList());

        EstadoCuentaResponseDTO response = new EstadoCuentaResponseDTO();
        response.setClienteId(clienteId);
        response.setCuentas(resumenes);

        return response;
    }
}