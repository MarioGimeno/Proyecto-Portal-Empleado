package com.example.proyecto_portal_empleado.model;

import com.example.proyecto_portal_empleado.contracts.ContractHistorialFichajes;
import com.example.proyecto_portal_empleado.entities.Fichaje;
import com.example.proyecto_portal_empleado.utils.FichajeService;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialFichajesModel implements ContractHistorialFichajes.Model {

    private FichajeService fichajeService;

    public HistorialFichajesModel() {
        fichajeService = RetrofitClient.getRetrofitInstance().create(FichajeService.class);
    }

    @Override
    public void obtenerHistorialFichajes(int usuarioId, OnFinishedListener onFinishedListener) {
        Call<List<Fichaje>> call = fichajeService.obtenerFichajesPorUsuario(usuarioId);
        call.enqueue(new Callback<List<Fichaje>>() {
            @Override
            public void onResponse(Call<List<Fichaje>> call, Response<List<Fichaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onFinishedListener.onHistorialFichajesCargado(response.body());
                } else {
                    onFinishedListener.onFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Fichaje>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
