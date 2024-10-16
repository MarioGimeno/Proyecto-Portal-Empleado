package com.example.proyecto_portal_empleado.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.proyecto_portal_empleado.contracts.ContractMisDatos;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.entities.Usuario;
import com.example.proyecto_portal_empleado.utils.MensajeService;
import com.example.proyecto_portal_empleado.utils.UsuarioService;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisDatosModel implements ContractMisDatos.Model {

    private UsuarioService usuarioService;
    private MensajeService mensajeService;
    private Context context;  // Necesario para acceder a SharedPreferences y otros recursos

    // Constructor que acepta el contexto
    public MisDatosModel(Context context) {
        this.context = context;
        initRetrofit();  // Inicializamos el servicio Retrofit
    }

    // Inicializar Retrofit
    private void initRetrofit() {
        usuarioService = RetrofitClient.getRetrofitInstance().create(UsuarioService.class);
        mensajeService = RetrofitClient.getRetrofitInstance().create(MensajeService.class);
    }

    @Override
    public void fetchUsuario(int id, OnFinishedListener listener) {
        usuarioService.obtenerUsuarioPorId(id).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    listener.onUsuarioLoaded(usuario);  // Notificar al listener que el usuario ha sido cargado
                } else {
                    listener.onFailure("Error al cargar los datos del usuario");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                listener.onFailure("Error de conexión al servidor");
                Log.e("MisDatosModel", "Error al cargar usuario", t);
            }
        });
    }

    @Override
    public void actualizarUsuario(int id, Usuario usuario, OnFinishedListener listener) {
        usuarioService.actualizarUsuario(id, usuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onUsuarioUpdated();  // Notificar que el usuario ha sido actualizado
                    Toast.makeText(context, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onFailure("Error al actualizar el usuario");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onFailure("Error de conexión al servidor");
            }
        });
    }

    @Override
    public void enviarMensaje(Mensaje mensaje, OnFinishedListener listener) {
        mensajeService.enviarMensaje(mensaje).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onMensajeEnviado();  // Notificar que el mensaje ha sido enviado
                    Toast.makeText(context, "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onFailure("Error al enviar el mensaje");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onFailure("Error de conexión al servidor al enviar el mensaje");
            }
        });
    }

}
