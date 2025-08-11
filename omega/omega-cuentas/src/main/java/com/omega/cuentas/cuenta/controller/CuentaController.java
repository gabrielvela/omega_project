package com.omega.cuentas.cuenta.controller;

import com.omega.cuentas.cuenta.dto.CuentaRequestDTO;
import com.omega.cuentas.cuenta.dto.CuentaResponseDTO;
import com.omega.cuentas.cuenta.dto.CuentaUpdateDTO;
import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.service.CuentaService;
import com.omega.cuentas.integration.exception.ClienteNoEncontradoException;
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

//    @GetMapping("/{numeroCuenta}")
//    public ResponseEntity<Cuenta> obtenerPorNumero(@PathVariable String numeroCuenta) {
//        return cuentaService.obtenerPorNumeroCuenta(numeroCuenta)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaResponseDTO> obtenerPorNumero(@PathVariable String numeroCuenta) {

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

            if (dto.getNombreCliente() == null || dto.getNombreCliente().isBlank()) {
                throw new IllegalArgumentException("El nombre del cliente es obligatorio");
            }

            Cuenta nueva = cuentaService.crearPorNombreCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (ClienteNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no existe: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error al crear cuenta: " + ex.getMessage());
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Cuenta> actualizar(@PathVariable Long id, @RequestBody Cuenta cuentaActualizada) {
//        Cuenta actualizada = cuentaService.actualizar(id, cuentaActualizada);
//        return ResponseEntity.ok(actualizada);
//    }

    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaResponseDTO> actualizarPorNumeroCuenta(
            @PathVariable String numeroCuenta,
            @RequestBody CuentaUpdateDTO cuentaActualizada) {

        CuentaResponseDTO actualizada = cuentaService.actualizarPorNumeroCuenta(numeroCuenta, cuentaActualizada);
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