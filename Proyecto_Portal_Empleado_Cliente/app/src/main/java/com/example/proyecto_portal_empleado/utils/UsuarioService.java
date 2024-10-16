package com.example.proyecto_portal_empleado.utils;

import com.example.proyecto_portal_empleado.entities.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {
    @GET("/usuarios/{id}")
    Call<Usuario> obtenerUsuarioPorId(@Path("id") int id);

    @PUT("/usuarios/{id}")
    Call<Void> actualizarUsuario(@Path("id") int id, @Body Usuario usuario);
}
