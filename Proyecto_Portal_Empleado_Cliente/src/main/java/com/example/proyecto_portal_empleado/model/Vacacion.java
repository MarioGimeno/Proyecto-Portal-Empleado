package com.example.proyecto_portal_empleado.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "vacacion")
public class Vacacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vacacion")
    private int idVacacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    @Column(name = "dias", nullable = false)
    private int dias;

    @Column(name = "aprobada", nullable = false)
    private boolean aprobada;

    // Cambiamos el tipo de "boolean" a "Boolean" para permitir valores null
    @Column(name = "pendiente")
    private Boolean pendiente;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)  // Relación uno a muchos
    private Usuario usuario;

    // Getters y Setters
    public int getIdVacacion() {
        return idVacacion;
    }

    public void setIdVacacion(int idVacacion) {
        this.idVacacion = idVacacion;
    }

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

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Cambiamos los métodos de acceso para manejar "Boolean" en lugar de "boolean"
    public Boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(Boolean pendiente) {
        this.pendiente = pendiente;
    }

    @Override
    public String toString() {
        return "Vacacion{" +
                "idVacacion=" + idVacacion +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", dias=" + dias +
                ", aprobada=" + aprobada +
                ", pendiente=" + pendiente + // Incluimos pendiente en el toString
                ", usuario=" + usuario +
                '}';
    }
}
