package com.example.proyecto_portal_empleado.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

    @Entity
    @Table(name="curso")
    public class Curso {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) //Dice que la generación de valores se genera en db, Autoincrement por ejemplo.
        @Column(name = "id_curso")
        private int idCurso;

        @Column(name = "curso", nullable = false)
        private String curso;

        @Column(name = "link_certificacion", nullable = false)
        private String linkCertificacion;

        @ManyToOne(fetch = FetchType.LAZY) // La carga perezosa significa que el usuario se carga solo cuando se accede a él
        @JoinColumn(name = "usuario_id", nullable = false) // Clave extranjera que apunta a la tabla de usuarios
        //@JsonIgnore
        private Usuario usuario;
        // Constructor vacío (necesario para JPA)
        public Curso() {
        }

    // Constructor
    public Curso(int idCurso, String curso, String linkCertificacion) {
        this.idCurso = idCurso;
        this.curso = curso;
        this.linkCertificacion = linkCertificacion;
    }

    // Getters y Setters
    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getLinkCertificacion() {
        return linkCertificacion;
    }

    public void setLinkCertificacion(String linkCertificacion) {
        this.linkCertificacion = linkCertificacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
