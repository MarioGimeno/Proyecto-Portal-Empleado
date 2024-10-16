package com.example.proyecto_portal_empleado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.cglib.core.Local;

import java.security.Timestamp;
import java.time.LocalDateTime;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class ArchivoDTO {
    private Long idArchivo;
    private String nombreArchivo;
    private String tipoArchivo;
    private String urlArchivo;
    private LocalDateTime fechaSubida;
    private Long usuarioId;  // Asegúrate de tener un campo para el usuario si es necesario

    // Constructor, getters y setters
    public ArchivoDTO(Archivo archivo) {
        this.idArchivo = archivo.getIdArchivo();
        this.nombreArchivo = archivo.getNombreArchivo();
        this.tipoArchivo = archivo.getTipoArchivo();
        this.urlArchivo = archivo.getUrlArchivo();
        this.fechaSubida = archivo.getFechaSubida();

    }

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

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

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}