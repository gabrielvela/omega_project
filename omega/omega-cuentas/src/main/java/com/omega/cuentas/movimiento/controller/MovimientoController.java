package com.omega.cuentas.movimiento.controller;

import com.omega.cuentas.movimiento.dto.MovimientoDTO;
import com.omega.cuentas.movimiento.dto.MovimientoRequestDTO;
import com.omega.cuentas.movimiento.model.Movimiento;
import com.omega.cuentas.movimiento.exception.CuentaInexistenteException;
import com.omega.cuentas.movimiento.service.MovimientoService;
import com.omega.cuentas.movimiento.exception.SaldoInsuficienteException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    // Obtener todos los movimientos
    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> listarTodos() {
        List<MovimientoDTO> movimientos = movimientoService.obtenerTodos();
        return ResponseEntity.ok(movimientos);
    }

    @PostMapping("/crear")
    public ResponseEntity<MovimientoDTO> crearMovimiento(@RequestBody MovimientoRequestDTO dto) {
        Movimiento movimiento = movimientoService.registrarMovimiento(dto);
        MovimientoDTO respuesta = new MovimientoDTO(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
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
                    movimientoDTO.getTipoMovimiento(),
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
