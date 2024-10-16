package com.example.proyecto_portal_empleado.model;

import com.example.proyecto_portal_empleado.utils.CustomLocalTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "fichajes")
public class Fichaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo", nullable = false)
    private String tipo;   // "Entrada" o "Salida"

    @JsonDeserialize(using = CustomLocalTimeDeserializer.class)
    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "fecha")
    private String fecha;

    @Transient // Not persisted; used to receive the user ID
    @JsonProperty("usuario_id")
    private int usuarioId;

    @Column(name = "tiempo_total_jornada")
    private String tiempo_total_jornada; // Changed from String to Long

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Constructors
    public Fichaje() {
    }

    // Updated constructor
    public Fichaje(String tipo, LocalTime hora, int usuarioId, String tiempo_total_jornada) {
        this.tipo = tipo;
        this.hora = hora;
        this.usuarioId = usuarioId;
        this.tiempo_total_jornada = tiempo_total_jornada;
    }

    // Getters and Setters

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTiempo_total_jornada() {
        return tiempo_total_jornada;
    }

    public void setTiempo_total_jornada(String tiempo_total_jornada) {
        this.tiempo_total_jornada = tiempo_total_jornada;
    }

    @Override
    public String toString() {
        return "Fichaje{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", hora=" + hora +
                ", fecha='" + fecha + '\'' +
                ", usuarioId=" + usuarioId +
                ", tiempo_total_jornada=" + tiempo_total_jornada +
                ", usuario=" + usuario +
                '}';
    }
}
