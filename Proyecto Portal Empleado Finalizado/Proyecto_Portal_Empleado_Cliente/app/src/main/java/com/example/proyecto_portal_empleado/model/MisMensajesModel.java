package com.example.proyecto_portal_empleado.model;

import com.example.proyecto_portal_empleado.contracts.ContractMisMensajes;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.utils.MensajeService;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisMensajesModel implements ContractMisMensajes.Model {

    private MensajeService mensajeService;

    public MisMensajesModel() {
        mensajeService = RetrofitClient.getRetrofitInstance().create(MensajeService.class);
    }

    @Override
    public void obtenerMensajes(int idUsuario, OnFinishedListener listener) {
        mensajeService.obtenerMensajes(idUsuario).enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onFinished(response.body());
                } else {
                    listener.onFailure("Mensajes no encontrados");
                }
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
