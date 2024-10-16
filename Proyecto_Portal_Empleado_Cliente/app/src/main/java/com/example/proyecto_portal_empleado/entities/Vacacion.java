package com.example.proyecto_portal_empleado.entities;

import com.google.gson.annotations.SerializedName;

public class Vacacion {

    private int idVacacion;
    private String nombreVacacion;
    private String fechaInicio;
    private String fechaFin;
    private int dias;
    private boolean aprobada;
    @SerializedName("pendiente")  // Asegúrate de que el nombre coincida con la clave del JSON
    private boolean isPendiente;

    private int idUsuario;  // Solo el ID del usuario

    // Constructor
    public Vacacion(int idVacacion, String nombreVacacion, String fechaInicio, String fechaFin, int dias, boolean aprobada, boolean isPendiente, int idUsuario) {
        this.idVacacion = idVacacion;
        this.nombreVacacion = nombreVacacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.dias = dias;
        this.aprobada = aprobada;
        this.isPendiente = isPendiente;
        this.idUsuario = idUsuario;  // Solo el ID del usuario
    }

    public Vacacion() {}

    public boolean isPendiente() {
        return isPendiente;
    }

    public void setPendiente(boolean pendiente) {
        isPendiente = pendiente;
    }

    // Getters y Setters
    public int getIdVacacion() {
        return idVacacion;
    }

    public void setIdVacacion(int idVacacion) {
        this.idVacacion = idVacacion;
    }

    public String getNombreVacacion() {
        return nombreVacacion;
    }

    public void setNombreVacacion(String nombreVacacion) {
        this.nombreVacacion = nombreVacacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Vacacion{" +
                "idVacacion=" + idVacacion +
                ", nombreVacacion='" + nombreVacacion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", dias=" + dias +
                ", aprobada=" + aprobada +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
