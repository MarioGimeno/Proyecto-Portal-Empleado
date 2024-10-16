package com.example.proyecto_portal_empleado.utils;

import org.springframework.cglib.core.Local;

import java.security.Timestamp;
import java.time.LocalDateTime;

public class ArchivoDTO {

    //CLASE POJO PARA APOYAR
    private String nombreArchivo;
    private String tipoArchivo;
    private LocalDateTime fechaSubida;

    // Constructor
    public ArchivoDTO(String nombreArchivo, String tipoArchivo, LocalDateTime fechaSubida) {
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.fechaSubida = fechaSubida;
    }

    // Getters y Setters
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
}
