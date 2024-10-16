package com.example.proyecto_portal_empleado.model;

import com.example.proyecto_portal_empleado.contracts.ContractFichajes;
import com.example.proyecto_portal_empleado.entities.Fichaje;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;
import com.example.proyecto_portal_empleado.utils.FichajeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichajesModel implements ContractFichajes.Model {

    private FichajeService fichajeService;

    public FichajesModel() {
        fichajeService = RetrofitClient.getRetrofitInstance().create(FichajeService.class);
    }

    @Override
    public void agregarFichaje(Fichaje fichaje, OnFinishedListener onFinishedListener) {
        fichajeService.agregarFichaje(fichaje).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFichajeAgregado(fichaje);
                } else {
                    onFinishedListener.onFailure("Error al agregar el fichaje");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void cargarFichajes(int usuarioId, OnFinishedListener onFinishedListener) {
        fichajeService.obtenerFichajesPorUsuario(usuarioId).enqueue(new Callback<List<Fichaje>>() {
            @Override
            public void onResponse(Call<List<Fichaje>> call, Response<List<Fichaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onFinishedListener.onFichajesCargados(response.body());
                } else {
                    onFinishedListener.onFailure("Error al cargar los fichajes");
                }
            }

            @Override
            public void onFailure(Call<List<Fichaje>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
