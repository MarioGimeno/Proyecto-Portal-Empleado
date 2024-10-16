package com.example.proyecto_portal_empleado.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.proyecto_portal_empleado.connection.RetrofitClient;
import com.example.proyecto_portal_empleado.contracts.ContractUser;
import com.example.proyecto_portal_empleado.presenters.UsuarioPresenter;
import com.example.proyecto_portal_empleado.requests.LoginRequest;
import com.example.proyecto_portal_empleado.responses.LoginResponse;
import com.example.proyecto_portal_empleado.utils.ApiService;
import com.example.proyecto_portal_empleado.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioModel implements ContractUser.Model {

    private UsuarioPresenter presenter;
    private ApiService apiService;
    private Context context;  // Contexto necesario para acceder a SharedPreferences y a las Activities

    // Modifica el constructor para aceptar un Context
    public UsuarioModel(UsuarioPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;  // Guarda el contexto recibido
    }

    @Override
    public void loginAPI(String email, String password, OnLoginUserListener onLoginUserListener) {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Crear el objeto LoginRequest
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Llamar a la API usando el objeto loginRequest
        Call<LoginResponse> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    // Guardar los datos del usuario en SharedPreferences
                    saveUserData(loginResponse);

                    // Redirigir a otra actividad después del login exitoso
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                    // Notificar al presentador que el login fue exitoso
                    onLoginUserListener.onFinished(loginResponse);
                } else {
                    // Notificar al presentador que hubo un error en el login
                    onLoginUserListener.onFailure("Correo o contraseña incorrectos");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Notificar al presentador que hubo un error en la conexión
                onLoginUserListener.onFailure("Error en la conexión");
            }
        });
    }

    // Método para guardar los datos del usuario en SharedPreferences
    private void saveUserData(LoginResponse usuario) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("idUsuario", usuario.getIdUsuario());
        editor.putString("nombre", usuario.getNombre());
        editor.putString("apellidos", usuario.getApellidos());
        editor.putString("email", usuario.getEmail());
        editor.putString("cuentaBancaria", usuario.getCuentaBancaria());
        editor.putString("numSeguridadSocial", usuario.getNumSeguridadSocial());
        editor.putString("tipoEmpleado", usuario.getTipoEmpleado().getNombre());

        // Guardar los cambios
        editor.apply();
    }
}

