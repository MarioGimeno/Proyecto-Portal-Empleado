package com.example.proyecto_portal_empleado.entities;

public class TipoEmpleado {
    private int id;
    private String nombre;

    // Constructor vacío (requerido por Retrofit)
    public TipoEmpleado() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoEmpleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
