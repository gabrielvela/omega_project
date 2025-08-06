package com.omega.count.cuenta.service;

import com.omega.count.cuenta.model.Cuenta;
import com.omega.count.cuenta.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> listarTodos() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> obtenerPorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    public boolean existePorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.existsByNumeroCuenta(numeroCuenta);
    }

    @Transactional
    public Cuenta crear(Cuenta cuenta) {
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
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void eliminar(Long id) {
        cuentaRepository.deleteById(id);
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