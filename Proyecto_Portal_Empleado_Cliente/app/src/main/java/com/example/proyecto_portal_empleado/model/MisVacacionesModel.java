package com.example.proyecto_portal_empleado.model;

import com.example.proyecto_portal_empleado.contracts.ContractMisVacaciones;
import com.example.proyecto_portal_empleado.entities.Usuario;
import com.example.proyecto_portal_empleado.entities.Vacacion;
import com.example.proyecto_portal_empleado.utils.UsuarioService;
import com.example.proyecto_portal_empleado.utils.VacacionesService;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisVacacionesModel implements ContractMisVacaciones.Model {
    private VacacionesService vacacionesService;
    private UsuarioService userService;
    public MisVacacionesModel() {
        vacacionesService = RetrofitClient.getRetrofitInstance().create(VacacionesService.class);
        userService = RetrofitClient.getRetrofitInstance().create(UsuarioService.class);

    }
    public void cargarVacacionesPorNombre(boolean isEditable, String nombre, OnFinishedListener listener) {
        vacacionesService.getVacacionesPorNombreUsuario(nombre).enqueue(new Callback<List<Vacacion>>() {
            @Override
            public void onResponse(Call<List<Vacacion>> call, Response<List<Vacacion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onVacacionesCargadas(response.body());
                } else {
                    listener.onFailure("No se encontraron vacaciones para el empleado.");
                }
            }

            @Override
            public void onFailure(Call<List<Vacacion>> call, Throwable t) {
                listener.onFailure("Error al cargar vacaciones: " + t.getMessage());
            }
        });
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
    public void obtenerUsuarioIdPorNombre(String nombre, OnUsuarioIdEncontradoListener listener) {
        // Suponemos que tienes un método en tu servicio para buscar usuarios por nombre
        userService.obtenerUsuarioPorNombre(nombre).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onUsuarioIdEncontrado(response.body().getIdUsuario());
                } else {
                    listener.onFailure("No se pudo encontrar un usuario con ese nombre");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                listener.onFailure("Error de conexión: " + t.getMessage());
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
