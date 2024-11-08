package com.example.proyecto_portal_empleado.utils;

import com.example.proyecto_portal_empleado.entities.Fichaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FichajeService {

    // Obtener fichajes por usuario
    @GET("fichajes/usuario/nombre")
    Call<List<Fichaje>> obtenerFichajesPorUsuario(@Query("idUsuario") String idUsuario);
    // Obtener fichajes por usuario
    @GET("fichajes/usuario")
    Call<List<Fichaje>> obtenerFichajesPorUsuario(@Query("idUsuario") int idUsuario);

    // Agregar un nuevo fichaje (POST)
    @POST("fichajes/agregar")
    Call<Void> agregarFichaje(@Body Fichaje nuevoFichaje);
}
