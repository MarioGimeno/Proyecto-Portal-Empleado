package com.example.proyecto_portal_empleado.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.adapters.ArchivoAdapter;
import com.example.proyecto_portal_empleado.contracts.ContractArchivos;
import com.example.proyecto_portal_empleado.entities.Archivo;
import com.example.proyecto_portal_empleado.presenters.MisArchivosPresenter;

import java.util.Collections;
import java.util.List;

public class MisArchivosActivity extends AppCompatActivity implements ContractArchivos.View {

    private MisArchivosPresenter presenter;
    private Spinner spinnerCategorias;
    private EditText etBuscarEmpleado;
    private Button btnBuscar, btnVolver;
    private String tipoEmpleado;
    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misarchivos);

        presenter = new MisArchivosPresenter(this);  // Inicializamos el presentador

        tipoEmpleado = obtenerTipoEmpleado();  // Obtener tipo de empleado
        configurarUI();

        if ("Admin".equals(tipoEmpleado)) {
            configurarAdmin();
        } else {
            usuarioId = obtenerUsuarioId();
            configurarEmpleado();
        }

        setupRecyclerView();
    }

    private void configurarUI() {
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        etBuscarEmpleado = findViewById(R.id.etBuscarEmpleado);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(MisArchivosActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Configurar Spinner de categorías
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoria = parent.getItemAtPosition(position).toString().toLowerCase();
                presenter.cargarArchivos(categoria, usuarioId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }

    private void configurarAdmin() {
        etBuscarEmpleado.setVisibility(View.VISIBLE);
        btnBuscar.setVisibility(View.VISIBLE);


        btnBuscar.setOnClickListener(v -> {
            String nombreBuscar = etBuscarEmpleado.getText().toString().trim();
            if (!nombreBuscar.isEmpty()) {
                // Realiza la llamada al presenter con el nombre y la categoría seleccionada
                presenter.cargarArchivosPorNombre(spinnerCategorias.getSelectedItem().toString().toLowerCase(), nombreBuscar);
            } else {
                Toast.makeText(this, "Por favor, ingrese un nombre", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarEmpleado() {
        etBuscarEmpleado.setVisibility(View.GONE);
        btnBuscar.setVisibility(View.GONE);
        presenter.cargarArchivos(spinnerCategorias.getSelectedItem().toString().toLowerCase(), usuarioId);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvArchivos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void mostrarArchivos(List<Archivo> archivos) {
        if (archivos != null && !archivos.isEmpty()) {
            Collections.reverse(archivos);  // Ordenar
            ArchivoAdapter adapter = new ArchivoAdapter(archivos, this::visualizarArchivo);
            RecyclerView recyclerView = findViewById(R.id.rvArchivos);
            recyclerView.setAdapter(adapter);
        } else {
            mostrarError("No se encontraron archivos.");
        }
    }

    @Override
    public void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void irAVisualizarArchivo(String urlArchivo) {
        Intent intent = new Intent(MisArchivosActivity.this, PdfViewerActivity.class);
        intent.putExtra("urlArchivo", urlArchivo);
        startActivity(intent);
    }

    private void visualizarArchivo(Archivo archivo) {
        String urlArchivo = archivo.getUrlArchivo(); // Obtener la URL del archivo
        irAVisualizarArchivo(urlArchivo);
    }

    private String obtenerTipoEmpleado() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("tipoEmpleado", "empleado");
    }

    private int obtenerUsuarioId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        return sharedPreferences.getInt("idUsuario", -1);
    }
}
