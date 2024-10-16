package com.example.proyecto_portal_empleado.responses;

import com.example.proyecto_portal_empleado.entities.Archivo;
import com.example.proyecto_portal_empleado.entities.Curso;
import com.example.proyecto_portal_empleado.entities.TipoEmpleado;
import com.example.proyecto_portal_empleado.entities.Vacacion;

import java.util.List;

public class LoginResponse {

    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String cuentaBancaria;
    private String numSeguridadSocial;
    private TipoEmpleado tipoEmpleado;
    private List<Curso> cursos;
    private List<Vacacion> vacaciones;
    private List<Archivo> archivos;

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

    @Override
    public String toString() {
        return "LoginResponse{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", cuentaBancaria='" + cuentaBancaria + '\'' +
                ", numSeguridadSocial='" + numSeguridadSocial + '\'' +
                ", tipoEmpleado=" + tipoEmpleado +
                ", cursos=" + cursos +
                ", vacaciones=" + vacaciones +
                ", archivos=" + archivos +
                '}';
    }
}
