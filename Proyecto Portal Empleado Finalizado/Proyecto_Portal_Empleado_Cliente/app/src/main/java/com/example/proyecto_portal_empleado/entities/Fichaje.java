package com.example.proyecto_portal_empleado.entities;

import com.google.gson.annotations.SerializedName;

public class Fichaje {
    private String tipo;
    private String hora;
    private String fecha;
    private int usuario_id;
    @SerializedName("tiempo_total_jornada")
    private String tiempoTotalJornada; // New field (in milliseconds)

    // Constructor for clock-in (without tiempoTotalJornada)
    public Fichaje(String tipo, String hora, String fecha, int usuario_id) {
        this.tipo = tipo;
        this.hora = hora;
        this.fecha = fecha;
        this.usuario_id = usuario_id;
    }

    // Constructor for clock-out (with tiempoTotalJornada)
    public Fichaje(String tipo, String hora, String fecha, int usuario_id, String tiempoTotalJornada) {
        this.tipo = tipo;
        this.hora = hora;
        this.fecha = fecha;
        this.usuario_id = usuario_id;
        this.tiempoTotalJornada = tiempoTotalJornada;
    }

    // Getters and setters

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getTiempoTotalJornada() {
        return tiempoTotalJornada;
    }

    public void setTiempoTotalJornada(String tiempoTotalJornada) {
        this.tiempoTotalJornada = tiempoTotalJornada;
    }

    @Override
    public String toString() {
        return "Fichaje{" +
                "tipo='" + tipo + '\'' +
                ", hora='" + hora + '\'' +
                ", fecha='" + fecha + '\'' +
                ", usuario_id=" + usuario_id +
                ", tiempoTotalJornada=" + tiempoTotalJornada +
                '}';
    }
    // ... existing getters and setters ...
}
