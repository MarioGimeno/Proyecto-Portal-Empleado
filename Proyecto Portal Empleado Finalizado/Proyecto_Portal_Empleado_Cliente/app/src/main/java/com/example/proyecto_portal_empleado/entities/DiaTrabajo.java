package com.example.proyecto_portal_empleado.entities;

public class DiaTrabajo {
    private String fecha; // Date of work
    private String horaEntrada; // Entry time
    private String horaSalida; // Exit time
    private String horasTotales; // Total hours worked

    // Constructor
    public DiaTrabajo(String fecha, String horaEntrada, String horaSalida, String horasTotales) {
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasTotales = horasTotales;
    }

    // Getters and Setters
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(String horaEntrada) { this.horaEntrada = horaEntrada; }

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }

    public String getHorasTotales() { return horasTotales; }
    public void setHorasTotales(String horasTotales) { this.horasTotales = horasTotales; }
}
