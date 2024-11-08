package com.example.proyecto_portal_empleado.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.contracts.ContractUser;
import com.example.proyecto_portal_empleado.entities.Usuario;
import com.example.proyecto_portal_empleado.presenters.UsuarioPresenter;
import com.example.proyecto_portal_empleado.responses.LoginResponse;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements ContractUser.View {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    UsuarioPresenter userPresenter = new UsuarioPresenter(this, this);  // Pasar la actividad como contexto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponenets();
    }

    private void initComponenets() {

        // Vincular las vistas
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validar las credenciales
                if (!isValidEmail(email)) {
                    Toast.makeText(LoginActivity.this, "Por favor, introduce un email válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, introduce una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mostrar el teclado solo cuando el usuario interactúe
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                userPresenter.login(email, password);
            }
        });
    }


    // Validación de email básica
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    @Override
    public void successLogin(LoginResponse user) {

    }

    @Override
    public void failureLogin(String err) {

    }


    public void onFailure(Call<LoginResponse> call, Throwable t) {
        // Error de red u otros fallos
        Log.e("LoginError", "Error de red: " + t.getMessage());
    }
}