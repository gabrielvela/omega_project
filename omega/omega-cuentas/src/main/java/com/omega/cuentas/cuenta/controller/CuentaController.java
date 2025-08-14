package com.omega.cuentas.cuenta.controller;

import com.omega.cuentas.cuenta.dto.CuentaCreateDTO;
import com.omega.cuentas.cuenta.dto.CuentaDTO;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarTodos() {
        List<CuentaDTO> cuentas = cuentaService.listarTodas();
        return ResponseEntity.ok(cuentas);
    }

    @PostMapping("/crear")
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody CuentaCreateDTO dto) {
        Cuenta cuenta = cuentaService.crearCuenta(dto);
        return ResponseEntity.ok(cuenta);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<CuentaDTO> buscarCuenta(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String numeroCuenta) {

        CuentaDTO cuentaDTO = cuentaService.buscarCuenta(id, numeroCuenta);
        return ResponseEntity.ok(cuentaDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<CuentaDTO> actualizarCuenta(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String numeroCuenta,
            @Valid @RequestBody CuentaUpdateDTO dto) {

        CuentaDTO actualizada = cuentaService.actualizarCuenta(id, numeroCuenta, dto);
        return ResponseEntity.ok(actualizada);
    }

    @PatchMapping("/actualizar-parcial")
    public ResponseEntity<CuentaDTO> actualizarParcialmente(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String numeroCuenta,
            @RequestBody Map<String, Object> campos) {

        CuentaDTO actualizada = cuentaService.actualizarParcialmente(id, numeroCuenta, campos);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Map<String, String>> eliminarCuenta(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String numeroCuenta) {

        cuentaService.eliminarCuenta(id, numeroCuenta);
        Map<String, String> respuesta = Map.of("mensaje", "Cuenta eliminada correctamente");
        return ResponseEntity.ok(respuesta);
    }
}
