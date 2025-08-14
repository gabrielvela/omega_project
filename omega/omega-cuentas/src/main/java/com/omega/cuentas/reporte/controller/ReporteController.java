package com.omega.cuentas.reporte.controller;

import com.omega.cuentas.integration.dto.Cliente;
import com.omega.cuentas.reporte.dto.EstadoCuentaDTO;
import com.omega.cuentas.reporte.dto.MovimientoReporteDTO;
import com.omega.cuentas.reporte.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }
    
    @GetMapping
    public ResponseEntity<List<MovimientoReporteDTO>> obtenerEstadoCuenta1(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String identificacion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        Cliente cli = new Cliente(clienteId, nombre, identificacion);

        List<MovimientoReporteDTO> reporte = reporteService.generarEstadoCuentaPorNombre(cli, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/estado-cuenta")
    public ResponseEntity<List<EstadoCuentaDTO>> obtenerEstadoCuentaAgrupado(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String identificacion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        Cliente cli = new Cliente(clienteId, nombre, identificacion);

        List<EstadoCuentaDTO> reporte = reporteService.generarEstadoCuentaAgrupado(cli, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

}
