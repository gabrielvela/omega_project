package com.omega.cuentas.cuenta.service;

import com.omega.cuentas.cuenta.dto.CuentaDTO;
import com.omega.cuentas.cuenta.dto.CuentaCreateDTO;
import com.omega.cuentas.cuenta.dto.CuentaResponseDTO;
import com.omega.cuentas.cuenta.dto.CuentaUpdateDTO;
import com.omega.cuentas.cuenta.model.Cuenta;
import com.omega.cuentas.cuenta.repository.CuentaRepository;
import com.omega.cuentas.integration.dto.Cliente;
import com.omega.cuentas.integration.validator.ClienteValidatorService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Service
//@RequiredArgsConstructor
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteValidatorService clienteValidatorService;

//    public CuentaService(CuentaRepository cuentaRepository, ClienteValidatorService clienteValidatorService) {
//        this.cuentaRepository = cuentaRepository;
//        this.clienteValidatorService = clienteValidatorService;
//    }
    public List<CuentaDTO> listarTodas() {
        return cuentaRepository.findAll().stream()
                .map(CuentaDTO::new)
                .collect(Collectors.toList());
    }

    private Cliente obtenerCliente(Cliente clienteInput) {
        if (clienteInput == null) {
            throw new IllegalArgumentException("Debe proporcionar información del cliente");
        }

        if (clienteInput.getClienteId() != null) {
//            log.info("Resolviendo cliente por ID: {}", clienteInput.getClienteId());
            System.out.println("Resolviendo cliente por ID: {}" + clienteInput.getClienteId());
            return clienteValidatorService.obtenerClientePorId(clienteInput.getClienteId());
        }

        if (clienteInput.getIdentificacion() != null) {
//            log.info("Resolviendo cliente por identificación: {}", clienteInput.getIdentificacion());
            System.out.println("Resolviendo cliente por identificación: {}" + clienteInput.getIdentificacion());
            return clienteValidatorService.obtenerClientePorIdentificacion(clienteInput.getIdentificacion());
        }

        if (clienteInput.getNombre() != null) {
//            log.info("Resolviendo cliente por nombre: {}", clienteInput.getNombre());
            System.out.println("Resolviendo cliente por nombre: {}" + clienteInput.getNombre());
            return clienteValidatorService.obtenerClientePorNombre(clienteInput.getNombre());
        }

        throw new IllegalArgumentException("Debe proporcionar clienteId, identificación o nombre");
    }

    public Cuenta crearCuenta(CuentaCreateDTO dto) {
        if (cuentaRepository.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese número");
        }

        // Resolver cliente desde el DTO
        Cliente cliente = obtenerCliente(dto.getCliente());

        // Ya no necesitas validar existencia por ID, porque el cliente ya fue obtenido
        // clienteValidatorService.validarExistenciaCliente(cliente.getClienteId()); ❌ innecesario
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(dto.getNumeroCuenta())
                .tipoCuenta(dto.getTipoCuenta())
                .saldoInicial(dto.getSaldoInicial())
                .saldoDisponible(dto.getSaldoInicial())
                .estado(true)
                .clienteId(cliente.getClienteId()) // Aquí ya tienes el ID real
                .build();

        Cuenta guardada = cuentaRepository.save(cuenta);
//        return new CuentaCreateDTO(guardada, cliente);
        return guardada;
    }

    public CuentaDTO actualizarCuenta(Long cuentaId, String numeroCuenta, CuentaUpdateDTO dto) {

        Cuenta cuenta = obtenerCuentaCriterios(cuentaId, numeroCuenta);

        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setEstado(dto.getEstado());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setSaldoDisponible(dto.getSaldoDisponible());

        Cuenta actualizada = cuentaRepository.save(cuenta);
        return new CuentaDTO(actualizada);
    }

    // 🔍 Método privado para buscar cuenta por id o número
    public Cuenta obtenerCuentaCriterios(Long id, String numeroCuenta) {

        if (id == null && numeroCuenta == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe proporcionar al menos un criterio de búsqueda");
        }

        if (id != null) {
            return cuentaRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta con ID " + id + " no encontrado"));

        } else if (numeroCuenta != null) {
            return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta con numeroCuenta " + numeroCuenta + " no encontrado"));
        } else {
            throw new IllegalArgumentException("Debe proporcionar un criterio de búsqueda");
        }
    }

    public CuentaDTO buscarCuenta(Long id, String numeroCuenta) {
        Cuenta cuenta = obtenerCuentaCriterios(id, numeroCuenta);
        return new CuentaDTO(cuenta);
    }

    public CuentaDTO actualizarParcialmente(Long id, String numeroCuenta, Map<String, Object> campos) {
        Cuenta cuenta = obtenerCuentaCriterios(id, numeroCuenta);

        campos.forEach((clave, valor) -> {
            Field campo = ReflectionUtils.findField(Cuenta.class, clave);
            if (campo != null) {
                campo.setAccessible(true);
                ReflectionUtils.setField(campo, cuenta, valor);
            }
        });

        Cuenta actualizada = cuentaRepository.save(cuenta);
        return new CuentaDTO(actualizada);
    }

    public void eliminarCuenta(Long id, String numeroCuenta) {
        Cuenta cuenta = obtenerCuentaCriterios(id, numeroCuenta);
        cuentaRepository.delete(cuenta);
    }

    public boolean existePorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.existsByNumeroCuenta(numeroCuenta);
    }

}
