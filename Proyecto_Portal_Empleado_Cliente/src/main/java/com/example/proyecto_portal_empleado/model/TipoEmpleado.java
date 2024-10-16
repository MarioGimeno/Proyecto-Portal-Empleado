package com.example.proyecto_portal_empleado.utils;

import jakarta.persistence.*;

@Entity
@Table(name="tipo_empleado")
public class TipoEmpleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_empleado")
    private Long idTipoEmpleado;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Constructor vacío (necesario para JPA)
    public TipoEmpleado() {
    }

    // Constructor con parámetros
    public TipoEmpleado(Long idTipoEmpleado, String nombre) {
        this.idTipoEmpleado = idTipoEmpleado;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getIdTipoEmpleado() {
        return idTipoEmpleado;
    }

    public void setIdTipoEmpleado(Long idTipoEmpleado) {
        this.idTipoEmpleado = idTipoEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
