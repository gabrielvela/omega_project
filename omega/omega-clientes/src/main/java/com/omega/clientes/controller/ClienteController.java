package com.omega.clientes.controller;

import com.omega.clientes.dto.ClienteCrearDTO;
import com.omega.clientes.dto.ClienteDTO;
import com.omega.clientes.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    @Autowired
    private final ClienteService clienteService;

//    public ClienteController(ClienteService clienteService) {
//        this.clienteService = clienteService;
//    }

    @GetMapping("/")
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/crear")
    public ResponseEntity<ClienteDTO> crear(@Valid @RequestBody ClienteCrearDTO clienteDTO) {
        ClienteDTO nuevo = clienteService.crear(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/buscar")
    public ResponseEntity<ClienteDTO> obtenerCliente(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String identificacion) {

        ClienteDTO cliente = clienteService.buscarCliente(id, identificacion, nombre);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ClienteDTO> actualizarCliente(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String identificacion,
            @Valid @RequestBody ClienteDTO clienteDTO) {

        ClienteDTO actualizado = clienteService.actualizarCliente(id, identificacion, clienteDTO);
        return ResponseEntity.ok(actualizado);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
//        clienteService.eliminar(id);
//        return ResponseEntity.noContent().build();
//    }
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarCliente(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String identificacion) {

        clienteService.eliminarCliente(id, identificacion);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<ClienteDTO> actualizarParcialmente(@PathVariable Long id, 
//            @RequestBody Map<String, Object> campos) {
//        ClienteDTO actualizado = clienteService.actualizarParcialmente(id, campos);
//        return ResponseEntity.ok(actualizado);
//    }
    @PatchMapping("/actualizar-parcial")
    public ResponseEntity<ClienteDTO> actualizarParcialmente(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String identificacion,
            @RequestBody Map<String, Object> campos) {

        ClienteDTO actualizado = clienteService.actualizarParcialmenteCliente(id, identificacion, campos);
        return ResponseEntity.ok(actualizado);
    }
}