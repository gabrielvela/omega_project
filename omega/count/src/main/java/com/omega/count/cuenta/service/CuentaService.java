package com.omega.count.cuenta.service;

import com.omega.count.cuenta.dto.CuentaDTO;
import com.omega.count.cuenta.model.Cuenta;
import com.omega.count.cuenta.repository.CuentaRepository;
import com.omega.count.integration.validator.ClienteValidatorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CuentaService {

    //@Autowired
    private CuentaRepository cuentaRepository;

    private final ClienteValidatorService clienteValidatorService;

    public CuentaService(CuentaRepository cuentaRepository, ClienteValidatorService clienteValidatorService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteValidatorService = clienteValidatorService;
    }


    public List<Cuenta> listarTodos() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> obtenerPorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    public boolean existePorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.existsByNumeroCuenta(numeroCuenta);
    }

//    @Transactional
//    public Cuenta crear(Cuenta cuenta) {
//        cuenta.setSaldoDisponible(cuenta.getSaldoInicial());
//        if (existePorNumeroCuenta(cuenta.getNumeroCuenta())) {
//            throw new IllegalArgumentException("Ya existe una cuenta con ese número.");
//        }
//        return cuentaRepository.save(cuenta);
//    }

    @Transactional
    public Cuenta crear(CuentaDTO cuentaDTO) {
        clienteValidatorService.validarExistenciaCliente(cuentaDTO.getClienteId());

        Cuenta cuenta = Cuenta.convertirDTOACuenta(cuentaDTO);
        cuenta.setSaldoDisponible(cuenta.getSaldoInicial());
        cuenta.setEstado(true);

        if (existePorNumeroCuenta(cuenta.getNumeroCuenta())) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese número.");
        }

        return cuentaRepository.save(cuenta);
    }


    @Transactional
    public Cuenta actualizar(Long id, Cuenta cuentaActualizada) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));
        cuenta.setTipoCuenta(cuentaActualizada.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaActualizada.getSaldoInicial());
        cuenta.setEstado(cuentaActualizada.getEstado());
        cuenta.setSaldoDisponible(cuentaActualizada.getSaldoDisponible());
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void eliminar(Long id) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);
        if (cuentaOpt.isPresent()) {
            cuentaRepository.delete(cuentaOpt.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta con ID " + id + " no existe");
        }
    }

    @Transactional
    public Cuenta actualizarParcialmente(Long id, Map<String, Object> camposActualizados) {
        return cuentaRepository.findById(id).map(cuenta -> {
            camposActualizados.forEach((key, value) -> {
                Field campo = ReflectionUtils.findField(Cuenta.class, key);
                if (campo != null) {
                    campo.setAccessible(true);
                    ReflectionUtils.setField(campo, cuenta, value);
                }
            });
            return cuentaRepository.save(cuenta);
        }).orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));
    }
}