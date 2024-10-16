package com.example.proyecto_portal_empleado.entities;

public class Mensaje {
    private int idUsuario;
    private String contenido;
    private String fecha;

    public Mensaje(int idUsuario, String contenido, String fecha) {
        this.idUsuario = idUsuario;
        this.contenido = contenido;
        this.fecha = fecha;
    }
    public Mensaje(){

    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "idUsuario=" + idUsuario +
                ", contenido='" + contenido + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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
