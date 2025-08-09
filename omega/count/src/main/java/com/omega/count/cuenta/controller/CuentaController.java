package com.omega.count.cuenta.controller;

import com.omega.count.cuenta.dto.CuentaDTO;
import com.omega.count.cuenta.dto.CuentaRequestDTO;
import com.omega.count.cuenta.model.Cuenta;
import com.omega.count.cuenta.service.CuentaService;
import com.omega.count.integration.exception.ClienteNoEncontradoException;
import com.omega.count.integration.validator.ClienteValidatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
//    public ResponseEntity<?> crear(@Valid @RequestBody CuentaDTO cuentaDTO) {
//        try {
//            Cuenta nueva = cuentaService.crear(cuentaDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
//        } catch (ClienteNoEncontradoException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no existe: " + ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body("Error al crear cuenta: " + ex.getMessage());
//        }
//
//    }


    @PostMapping
    public ResponseEntity<?> crearCuentaPorNombre(@Valid @RequestBody CuentaRequestDTO dto) {

        try {
            Cuenta nueva = cuentaService.crearPorNombreCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (ClienteNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no existe: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error al crear cuenta: " + ex.getMessage());
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