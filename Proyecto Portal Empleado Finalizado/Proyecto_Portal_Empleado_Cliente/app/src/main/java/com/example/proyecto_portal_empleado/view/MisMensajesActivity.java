package com.example.proyecto_portal_empleado.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.adapters.MensajesAdapter;
import com.example.proyecto_portal_empleado.contracts.ContractMisMensajes;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.presenters.MisMensajesPresenter;

import java.util.Collections;
import java.util.List;

public class MisMensajesActivity extends AppCompatActivity implements ContractMisMensajes.View {

    private RecyclerView rvMensajes;
    private MensajesAdapter mensajesAdapter;
    private Button btnVolver;
    private MisMensajesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mismensajes);

        // Inicializar el presentador
        presenter = new MisMensajesPresenter(this);

        // Configurar RecyclerView y Adapter
        rvMensajes = findViewById(R.id.rvMensajes);
        rvMensajes.setLayoutManager(new LinearLayoutManager(this));
        mensajesAdapter = new MensajesAdapter(Collections.emptyList());
        rvMensajes.setAdapter(mensajesAdapter);

        // Configurar el botón "Volver"
        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> finish());

        // Obtener ID del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt("idUsuario", -1);

        // Verificar que el ID es válido
        if (idUsuario != -1) {
            presenter.cargarMensajes(idUsuario);
        } else {
            Toast.makeText(this, "Error: ID de usuario no encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void showMensajes(List<Mensaje> mensajes) {
        Collections.reverse(mensajes);
        mensajesAdapter.setMensajesList(mensajes);
        mensajesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        // Mostrar algún indicador de carga si es necesario
    }

    @Override
    public void hideLoading() {
        // Ocultar el indicador de carga
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
