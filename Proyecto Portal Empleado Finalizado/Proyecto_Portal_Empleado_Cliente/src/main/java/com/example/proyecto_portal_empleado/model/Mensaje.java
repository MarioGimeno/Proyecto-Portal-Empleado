package com.example.proyecto_portal_empleado.utils;

import jakarta.persistence.*;
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

    @Column(name = "fecha", nullable = false)
    private String fecha;  // Fecha del mensaje

    // Constructor vacío (requerido por JPA)
    public Mensaje() {}

    // Constructor para inicializar un mensaje con parámetros
    public Mensaje(Integer idUsuario, String contenido, String fecha) {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
