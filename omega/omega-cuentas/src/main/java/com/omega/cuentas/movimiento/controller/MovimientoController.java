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
    @GetMapping("/buscar")
    public ResponseEntity<MovimientoDTO> buscarMovimiento(@RequestParam(required = false) Long id) {
        MovimientoDTO movimientoDto = movimientoService.obtenerPorId(id);
        return ResponseEntity.ok(movimientoDto);
    }

    //Actualizar movimiento se encuentra funcional, sin embargo,
    //de acuerdo a mi lógica no debe existir este método o talvez debe crear otro movimiento que revierta el movimiento que se va a modificar
    /*@PutMapping("/actualizar")
    public ResponseEntity<MovimientoDTO> actualizarMovimiento(
            @RequestParam Long id,
            @RequestBody @Valid MovimientoDTO dto) {

        Movimiento actualizado = movimientoService.actualizarMovimiento(id, dto);
        MovimientoDTO respuesta = new MovimientoDTO(actualizado);
        return ResponseEntity.ok(respuesta);
    }*/
    
    
    //Eliminar movimiento se encuentra funcional, sin embargo,
    //de acuerdo a mi lógica no debe existir este método o talvez debe crear otro movimiento que revierta el movimiento que se va a modificar
    /*@DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminar(
            @RequestParam(required = false) Long id) {
        boolean eliminado = movimientoService.eliminarMovimiento(id);
        if (eliminado) {
            return ResponseEntity.ok("Movimiento eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movimiento no encontrado");
        }
    }*/

}
