package com.example.proyecto_portal_empleado.utils;

import com.example.proyecto_portal_empleado.entities.Archivo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ArchivoService {




    @GET("/archivos/categoria/{categoria}/usuario/{usuarioId}")
    Call<List<Archivo>> getArchivosByCategoriaYUsuarioId(@Path("categoria") String categoria, @Path("usuarioId") int usuarioId);

}
