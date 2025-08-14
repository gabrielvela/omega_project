package com.omega.cuentas.reporte.controller;

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

//    @GetMapping()
//    public ResponseEntity<List<MovimientoReporteDTO>> obtenerEstadoCuentaPorId(
//            @RequestParam Long clienteId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
//
//        List<MovimientoReporteDTO> reporte = reporteService.generarEstadoCuenta(clienteId, fechaInicio, fechaFin);
//        return ResponseEntity.ok(reporte);
//    }
    @GetMapping
    public ResponseEntity<List<MovimientoReporteDTO>> obtenerEstadoCuentaPorNombre(
            @RequestParam(required = false) String nombreCliente,
            @RequestParam(required = false) Long idCliente,
            @RequestParam(required = false) String identificacion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<MovimientoReporteDTO> reporte = reporteService.generarEstadoCuentaPorNombre(nombreCliente, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/estado-cuenta")
    public ResponseEntity<List<EstadoCuentaDTO>> obtenerEstadoCuentaAgrupado(
            @RequestParam(required = false) String nombreCliente,
            @RequestParam(required = false) Long idCliente,
            @RequestParam(required = false) String identificacion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<EstadoCuentaDTO> reporte = reporteService.generarEstadoCuentaAgrupado(nombreCliente, idCliente, identificacion, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

}
