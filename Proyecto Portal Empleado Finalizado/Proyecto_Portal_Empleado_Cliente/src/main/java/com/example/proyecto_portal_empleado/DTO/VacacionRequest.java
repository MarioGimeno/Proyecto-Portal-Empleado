package com.example.proyecto_portal_empleado.DTO;

import java.sql.Date;

public class VacacionRequest {

    private Date fechaInicio;  // Usamos String para recibir fechas en formato JSON
    private Date fechaFin;     // Lo mismo para la fecha de fin
    private int dias;
    private int idUsuario;      // El ID del usuario que se asocia a la vacación

    // Constructor vacío
    public VacacionRequest() {
    }

    // Constructor con parámetros
    public VacacionRequest(Date fechaInicio, Date fechaFin, int dias, int idUsuario) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.dias = dias;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
