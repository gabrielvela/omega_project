package com.omega.count.reporte.dto;

import java.util.List;

public class EstadoCuentaResponseDTO {
    private Long clienteId;
    private List<CuentaResumenDTO> cuentas;

    // Getters y setters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<CuentaResumenDTO> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaResumenDTO> cuentas) {
        this.cuentas = cuentas;
    }
}
