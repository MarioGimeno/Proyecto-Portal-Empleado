package com.example.proyecto_portal_empleado.model;

import jakarta.persistence.*;

@Entity
@Table(name="usuarios_vacaciones")
public class UsuarioVacacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario_vacaciones")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacacion_id")
    private Vacacion vacacion;

    // Constructor vacío
    public UsuarioVacacion() {
    }

    // Constructor con parámetros
    public UsuarioVacacion(Usuario usuario, Vacacion vacacion) {
        this.usuario = usuario;
        this.vacacion = vacacion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vacacion getVacacion() {
        return vacacion;
    }

    public void setVacacion(Vacacion vacacion) {
        this.vacacion = vacacion;
    }
}
