package com.example.proyecto_portal_empleado.model;

import android.util.Log;

import com.example.proyecto_portal_empleado.contracts.ContractArchivos;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;
import com.example.proyecto_portal_empleado.entities.Archivo;
import com.example.proyecto_portal_empleado.utils.ArchivoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisArchivosModel implements ContractArchivos.Model {

    private ArchivoService archivoService;
    private ContractArchivos.Presenter presenter;

    public MisArchivosModel(ContractArchivos.Presenter presenter) {
        this.presenter = presenter;
        archivoService = RetrofitClient.getRetrofitInstance().create(ArchivoService.class); // Inicializa Retrofit
    }

    @Override
    public void obtenerArchivos(String categoria, int usuarioId) {
        archivoService.getArchivosByCategoriaYUsuarioId(categoria, usuarioId).enqueue(new Callback<List<Archivo>>() {
            @Override
            public void onResponse(Call<List<Archivo>> call, Response<List<Archivo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    presenter.onArchivoCargado(response.body());
                } else {
                    presenter.onError("Error al cargar archivos.");
                }
            }

            @Override
            public void onFailure(Call<List<Archivo>> call, Throwable t) {
                presenter.onError("Error: " + t.getMessage());
            }
        });
    }
}
