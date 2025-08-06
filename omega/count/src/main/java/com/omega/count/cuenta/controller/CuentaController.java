package com.omega.count.cuenta.controller;

import com.omega.count.cuenta.model.Cuenta;
import com.omega.count.cuenta.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> obtenerTodas() {
        return cuentaService.listarTodos();
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> obtenerPorNumero(@PathVariable String numeroCuenta) {
        return cuentaService.obtenerPorNumeroCuenta(numeroCuenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<Cuenta> crear(@RequestBody Cuenta cuenta) {
//        Cuenta nueva = cuentaService.crear(cuenta);
//        return ResponseEntity.ok(nueva);
//    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Cuenta cuenta) {
        try {
            Cuenta nueva = cuentaService.crear(cuenta);
            return ResponseEntity.ok(nueva);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .badRequest()
                    .body("Error al crear cuenta: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizar(@PathVariable Long id, @RequestBody Cuenta cuentaActualizada) {
        Cuenta actualizada = cuentaService.actualizar(id, cuentaActualizada);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        try {
            Cuenta actualizada = cuentaService.actualizarParcialmente(id, campos);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }
}