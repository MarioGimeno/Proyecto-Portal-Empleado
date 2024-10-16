package com.example.proyecto_portal_empleado.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private int id;  // ID del mensaje (generado automáticamente)

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;  // ID del usuario que realizó la modificación

    @Column(name = "contenido", nullable = false)
    private String contenido;  // Contenido del mensaje
    @Column(name = "fecha")
    private LocalDate fecha; // Cambiar a LocalDate

    // Constructor vacío (requerido por JPA)
    public Mensaje() {}

    // Constructor para inicializar un mensaje con parámetros
    public Mensaje(Integer idUsuario, String contenido, LocalDate fecha) {
        this.idUsuario = idUsuario;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDate getFecha() {
        return fecha;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", contenido='" + contenido + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
