package com.example.proyecto_portal_empleado.utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

    @Entity
    @Table(name="usuario")
    public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_usuario",nullable = false)
        private int idUsuario;

        @Column(name = "nombre",nullable = false)
        private String nombre;

        @Column(name = "apellidos",nullable = false)
        private String apellidos;


        @Column(name = "email",nullable = false)
        private String email;

        @Column(name="password", nullable = false)
        private String password;

        @Column(name="cuenta_bancaria")
        private String cuentaBancaria;

        @Column(name="num_seguridad_social")
        private String numSeguridadSocial;

        @ManyToOne
        @JoinColumn(name = "tipoEmpleado_id") // Relación muchos a uno con TipoEmpleado
        private TipoEmpleado tipoEmpleado;

        @OneToMany(mappedBy = "usuario")
        @JsonIgnore
        private List<Curso> cursos;

        @OneToMany(mappedBy = "usuario")
        @JsonIgnore
        private List<Vacacion> vacaciones;

        @OneToMany(mappedBy = "usuario")
        @JsonIgnore
        private List<Archivo> archivos;

        // Relación OneToMany con la entidad Fichaje
        @OneToMany(mappedBy = "usuario")
        @JsonIgnore
        private List<Fichaje> fichajes;



        // Constructor
    public Usuario(int idUsuario, String nombre, String apellidos, String correo,
                   String contrasena, String cuentaBancaria, String numSeguridadSocial,
                   TipoEmpleado tipoEmpleado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = correo;
        this.password = contrasena;
        this.cuentaBancaria = cuentaBancaria;
        this.numSeguridadSocial = numSeguridadSocial;
        this.tipoEmpleado = tipoEmpleado;
    }
    public Usuario(){

    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String contrasena) {
        this.password = contrasena;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getNumSeguridadSocial() {
        return numSeguridadSocial;
    }

    public void setNumSeguridadSocial(String numSeguridadSocial) {
        this.numSeguridadSocial = numSeguridadSocial;
    }

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

        public List<Fichaje> getFichajes() {
            return fichajes;
        }

        public void setFichajes(List<Fichaje> fichajes) {
            this.fichajes = fichajes;
        }

        public List<Vacacion> getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(List<Vacacion> vacaciones) {
        this.vacaciones = vacaciones;
    }

    public List<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<Archivo> archivos) {
        this.archivos = archivos;
    }
}
