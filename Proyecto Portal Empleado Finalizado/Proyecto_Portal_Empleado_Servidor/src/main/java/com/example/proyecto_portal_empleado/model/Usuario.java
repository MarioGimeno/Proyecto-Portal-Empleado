package com.example.proyecto_portal_empleado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "cuenta_bancaria")
    private String cuentaBancaria;

    @Column(name = "num_seguridad_social")
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

    // Nuevas columnas añadidas
    @Column(name = "puesto_trabajo")
    private String puestoTrabajo;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "fecha_contratacion")
    private String fechaContratacion;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;


    // Constructor
    public Usuario(int idUsuario, String nombre, String apellidos, String correo,
                   String contrasena, String cuentaBancaria, String numSeguridadSocial,
                   TipoEmpleado tipoEmpleado, String puestoTrabajo, String departamento,
                   String fechaContratacion, String telefonoContacto, String direccion,
                   String fechaNacimiento) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = correo;
        this.password = contrasena;
        this.cuentaBancaria = cuentaBancaria;
        this.numSeguridadSocial = numSeguridadSocial;
        this.tipoEmpleado = tipoEmpleado;
        this.puestoTrabajo = puestoTrabajo;
        this.departamento = departamento;
        this.fechaContratacion = fechaContratacion;
        this.telefonoContacto = telefonoContacto;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario() {

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

    // Getters y Setters para las nuevas columnas

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
                ", puestoTrabajo='" + puestoTrabajo + '\'' +
                ", departamento='" + departamento + '\'' +
                ", fechaContratacion='" + fechaContratacion + '\'' +
                ", telefonoContacto='" + telefonoContacto + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }
}
