package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.utils.TipoEmpleado;

public class LoginResponse {
    private TipoEmpleado tipoEmpleado;
    private int idUsuario;

    public LoginResponse(TipoEmpleado tipoEmpleado, int idUsuario) {
        this.tipoEmpleado = tipoEmpleado;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "tipoEmpleado=" + tipoEmpleado +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
