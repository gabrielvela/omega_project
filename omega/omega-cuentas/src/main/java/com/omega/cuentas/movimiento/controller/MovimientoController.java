package com.omega.cuentas.movimiento.controller;

import com.omega.cuentas.movimiento.dto.MovimientoDTO;
import com.omega.cuentas.movimiento.dto.MovimientoRequestDTO;
import com.omega.cuentas.movimiento.model.Movimiento;
import com.omega.cuentas.movimiento.service.CuentaInexistenteException;
import com.omega.cuentas.movimiento.service.MovimientoService;
import com.omega.cuentas.movimiento.service.SaldoInsuficienteException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

//    public MovimientoController(MovimientoService movimientoService) {
//        this.movimientoService = movimientoService;
//    }
    
    
    // Obtener todos los movimientos
    @GetMapping
    public ResponseEntity<List<Movimiento>> listarTodos() {
        List<Movimiento> movimientos = movimientoService.listarMovimientos();
        return ResponseEntity.ok(movimientos);
    }
    
//        @PostMapping
//    public ResponseEntity<Void> registrar(@RequestBody MovimientoRequestDTO dto) {
//        movimientoService.registrarPorNumeroCuenta(dto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

//    @PostMapping
//    public ResponseEntity<?> registrar(@RequestBody MovimientoDTO movimientoDTO) {
//        try {
//            Movimiento nuevo = movimientoService.registrarMovimientoConNumeroCuenta(
//                    movimientoDTO.getCuentaId(),
//                    movimientoDTO.getTipo(),
//                    movimientoDTO.getValor()
//            );
//            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
//        } catch (SaldoInsuficienteException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (CuentaInexistenteException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
    @PostMapping
    public ResponseEntity<?> registrarConNumeroCuenta(@RequestBody MovimientoRequestDTO movimientoDTO) {
        try {
            Movimiento nuevo = movimientoService.registrarMovimientoConNumeroCuenta(
                    movimientoDTO.getNumeroCuenta(),
                    movimientoDTO.getTipo(),
                    movimientoDTO.getValor()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (CuentaInexistenteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Obtener movimiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Movimiento movimiento = movimientoService.obtenerPorId(id);
        if (movimiento != null) {
            return ResponseEntity.ok(movimiento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movimiento no encontrado");
        }
    }
    
        // Actualizar movimiento (si aplica en tu lógica)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPorIdMovimiento(@PathVariable Long id, @RequestBody @Valid MovimientoDTO movimientoDTO) {
        try {
            Movimiento actualizado = movimientoService.actualizarMovimiento(
                    id,
                    movimientoDTO.getTipo(),
                    movimientoDTO.getValor()
            );
            return ResponseEntity.ok(actualizado);
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (CuentaInexistenteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar movimiento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = movimientoService.eliminarMovimiento(id);
        if (eliminado) {
            return ResponseEntity.ok("Movimiento eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movimiento no encontrado");
        }
    }



}
