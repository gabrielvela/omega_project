package com.omega.cuentas.reporte.controller;

import com.omega.cuentas.reporte.dto.EstadoCuentaResponseDTO;
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
//    public ResponseEntity<List<MovimientoReporteDTO>> obtenerEstadoCuenta(
//            @RequestParam Long clienteId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
//
//        List<MovimientoReporteDTO> reporte = reporteService.generarEstadoCuenta(clienteId, fechaInicio, fechaFin);
//        return ResponseEntity.ok(reporte);
//    }
    
    @GetMapping
    public ResponseEntity<List<MovimientoReporteDTO>> obtenerEstadoCuentaPorNombre(
            @RequestParam String nombreCliente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<MovimientoReporteDTO> reporte = reporteService.generarEstadoCuentaPorNombre(nombreCliente, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

}
