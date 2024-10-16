package com.example.proyecto_portal_empleado.utils;


import jakarta.persistence.*;


import java.io.Serializable;

@Entity
@Table(name = "fichajes")
public class Fichaje implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único para el fichaje

    private String tipo;   // "Entrada" o "Salida"
    private String hora;
    private String fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;

    public Fichaje() {}

    public Fichaje(String tipo, String hora, String fecha, Usuario usuario) {
        this.tipo = tipo;
        this.hora = hora;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Fichaje{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", hora='" + hora + '\'' +
                ", fecha='" + fecha + '\'' +
                ", usuario=" + usuario.getIdUsuario() +  // Mostramos solo el ID del usuario en el toString
                '}';
    }
}
