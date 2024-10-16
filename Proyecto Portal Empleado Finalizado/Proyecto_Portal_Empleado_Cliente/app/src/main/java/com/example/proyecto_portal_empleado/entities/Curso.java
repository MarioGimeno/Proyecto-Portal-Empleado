package com.example.proyecto_portal_empleado.entities;


public class Curso {

    private int idCurso;
    private String curso;
    private String linkCertificacion;

    // Constructor vacío
    public Curso() {}

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

    @Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", curso='" + curso + '\'' +
                ", linkCertificacion='" + linkCertificacion + '\'' +
                '}';
    }
}
