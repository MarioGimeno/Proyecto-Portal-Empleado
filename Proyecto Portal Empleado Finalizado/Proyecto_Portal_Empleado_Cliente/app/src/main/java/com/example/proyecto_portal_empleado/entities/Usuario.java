package com.example.proyecto_portal_empleado.entities;

import java.util.List;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String cuentaBancaria;
    private String numSeguridadSocial;
    private TipoEmpleado tipoEmpleado;
    private List<Curso> cursos;
    private List<Vacacion> vacaciones;
    private List<Archivo> archivos;
    private List<Fichaje> fichajes;  // Nueva lista de fichajes

    // Nuevos campos añadidos
    private String puestoTrabajo;
    private String departamento;
    private String fechaContratacion;
    private String telefonoContacto;
    private String direccion;
    private String fechaNacimiento;

    // Constructor vacío
    public Usuario() {}

    // Constructor que acepta solo el ID del usuario
    public Usuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Constructor con los campos que estás utilizando actualmente
    public Usuario(String nombre, String apellidos, String email,
                   String cuentaBancaria, String numSeguridadSocial,
                   String departamento, String fechaContratacion,
                   String fechaNacimiento, String telefonoContacto,
                   String puestoTrabajo, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.cuentaBancaria = cuentaBancaria;
        this.numSeguridadSocial = numSeguridadSocial;
        this.departamento = departamento;
        this.fechaContratacion = fechaContratacion;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoContacto = telefonoContacto;
        this.puestoTrabajo = puestoTrabajo;
        this.direccion = direccion;
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

    public void setPassword(String password) {
        this.password = password;
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

    public List<Fichaje> getFichajes() {
        return fichajes;
    }

    public void setFichajes(List<Fichaje> fichajes) {
        this.fichajes = fichajes;
    }

    // Agregar un fichaje a la lista
    public void agregarFichaje(Fichaje fichaje) {
        this.fichajes.add(fichaje);
    }

    // Getters y Setters para los nuevos campos

    public String getPuestoTrabajo() {
        return puestoTrabajo;
    }

    public void setPuestoTrabajo(String puestoTrabajo) {
        this.puestoTrabajo = puestoTrabajo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cuentaBancaria='" + cuentaBancaria + '\'' +
                ", numSeguridadSocial='" + numSeguridadSocial + '\'' +
                ", tipoEmpleado=" + tipoEmpleado +
                ", cursos=" + cursos +
                ", vacaciones=" + vacaciones +
                ", archivos=" + archivos +
                ", fichajes=" + fichajes +
                ", puestoTrabajo='" + puestoTrabajo + '\'' +
                ", departamento='" + departamento + '\'' +
                ", fechaContratacion='" + fechaContratacion + '\'' +
                ", telefonoContacto='" + telefonoContacto + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }
}
