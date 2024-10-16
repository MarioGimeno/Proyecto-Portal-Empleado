package com.example.proyecto_portal_empleado.entities;

public class Archivo {

    private Long idArchivo;
    private String nombreArchivo;
    private String tipoArchivo;
    private String urlArchivo;  // Actualizado de archivoBase64 a urlArchivo
    private String categoria;  // Nuevo campo para la categoría (ej. contrato, nómina)
    private Usuario usuario;
    private String fechaSubida;

    // Constructor vacío
    public Archivo() {}

    // Constructor completo
    public Archivo(String nombreArchivo, String tipoArchivo, String urlArchivo, String categoria, Usuario usuario, String fechaSubida) {
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.urlArchivo = urlArchivo;
        this.categoria = categoria;
        this.usuario = usuario;
        this.fechaSubida = fechaSubida;
    }

    // Getters y Setters
    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    @Override
    public String toString() {
        return "Archivo{" +
                "idArchivo=" + idArchivo +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                ", tipoArchivo='" + tipoArchivo + '\'' +
                ", urlArchivo='" + urlArchivo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", usuario=" + usuario +
                ", fechaSubida=" + fechaSubida +
                '}';
    }
}
