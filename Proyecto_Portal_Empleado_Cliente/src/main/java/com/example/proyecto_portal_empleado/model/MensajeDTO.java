package com.example.proyecto_portal_empleado.utils;

public class MensajeDTO {

    private Integer idUsuario;  // ID del usuario
    private String contenido;   // Contenido del mensaje
    private String fecha;       // Fecha en formato String (ISO 8601)

    // Constructor vacío
    public MensajeDTO() {}

    // Constructor con parámetros
    public MensajeDTO(Integer idUsuario, String contenido, String fecha) {
        this.idUsuario = idUsuario;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

