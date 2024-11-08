package com.example.proyecto_portal_empleado.utils;

import com.example.proyecto_portal_empleado.entities.UsuarioVacacion;
import com.example.proyecto_portal_empleado.entities.Vacacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VacacionesService {

    // Obtener todas las vacaciones disponibles
    @GET("vacaciones/usuario/{id}")
    Call<List<Vacacion>> obtenerVacacionesPorUsuario(@Path("id") int usuarioId);

    @GET("/vacaciones/usuario/nombre")
    Call<List<Vacacion>> getVacacionesPorNombreUsuario(@Query("nombre") String nombre);
    // Aprobar una vacación
    @PUT("/vacaciones/{id}/aprobar")
    Call<Void> aprobarVacacion(@Path("id") int id);

    // Aprobar una vacación
    @PUT("/vacaciones/{id}/rechazar")
    Call<Void> rechazarVacacion(@Path("id") int id);

    // Eliminar una vacación por ID
    @DELETE("/vacaciones/{id}")
    Call<Void> eliminarVacacion(@Path("id") int idVacacion);

    // Agregar vacaciones a un usuario (asegúrate de tener este endpoint en el controlador)
    @POST("/vacaciones/agregar")
    Call<Void> agregarVacacion(@Body Vacacion vacacion);


    // Modificar vacaciones para un usuario (asegúrate de tener este endpoint en el controlador)
    @PUT("/vacaciones/usuario/modificar")
    Call<Void> modificarVacacionesUsuario(@Body UsuarioVacacion usuarioVacacion);
}
