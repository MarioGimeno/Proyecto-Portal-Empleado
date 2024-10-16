package com.example.proyecto_portal_empleado.model;

import com.example.proyecto_portal_empleado.contracts.ContractMisVacaciones;
import com.example.proyecto_portal_empleado.entities.Vacacion;
import com.example.proyecto_portal_empleado.utils.VacacionesService;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisVacacionesModel implements ContractMisVacaciones.Model {
    private VacacionesService vacacionesService;

    public MisVacacionesModel() {
        vacacionesService = RetrofitClient.getRetrofitInstance().create(VacacionesService.class);
    }

    @Override
    public void cargarVacaciones(boolean isEditable, int usuarioId, OnFinishedListener listener) {
        vacacionesService.obtenerVacacionesPorUsuario(usuarioId).enqueue(new Callback<List<Vacacion>>() {
            @Override
            public void onResponse(Call<List<Vacacion>> call, Response<List<Vacacion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onVacacionesCargadas(response.body());
                } else {
                    listener.onFailure("Error al cargar las vacaciones");
                }
            }

            @Override
            public void onFailure(Call<List<Vacacion>> call, Throwable t) {
                listener.onFailure("Error de conexión");
            }
        });
    }

    @Override
    public void agregarVacacion(String fechaInicio, String fechaFin, int diasRequeridos, int usuarioId, OnFinishedListener listener) {
        Vacacion nuevaVacacion = new Vacacion();
        nuevaVacacion.setFechaInicio(fechaInicio);
        nuevaVacacion.setFechaFin(fechaFin);
        nuevaVacacion.setDias(diasRequeridos);
        nuevaVacacion.setIdUsuario(usuarioId);
        nuevaVacacion.setPendiente(true);
        nuevaVacacion.setAprobada(false);

        vacacionesService.agregarVacacion(nuevaVacacion).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onVacacionAgregada();
                } else {
                    listener.onFailure("Error al agregar vacación");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onFailure("Error de conexión");
            }
        });
    }
}
