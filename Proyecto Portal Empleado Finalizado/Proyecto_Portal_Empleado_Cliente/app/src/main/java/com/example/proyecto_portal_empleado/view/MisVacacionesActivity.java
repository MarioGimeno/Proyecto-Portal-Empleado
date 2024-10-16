package com.example.proyecto_portal_empleado.view;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.adapters.VacacionesAdapter;
import com.example.proyecto_portal_empleado.contracts.ContractMisVacaciones;
import com.example.proyecto_portal_empleado.entities.Vacacion;
import com.example.proyecto_portal_empleado.presenters.MisVacacionesPresenter;
import com.example.proyecto_portal_empleado.utils.VacacionesService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MisVacacionesActivity extends AppCompatActivity implements ContractMisVacaciones.View, VacacionesAdapter.OnVacacionInteractionListener {

    private RecyclerView rvVacaciones;
    private TextView tvDiasRestantes;
    private EditText etBuscarEmpleado;
    private Button btnAgregarVacacion, btnBuscar, btnVolver;
    private MisVacacionesPresenter presenter;
    private int usuarioId;
    private boolean isEditable;
    private VacacionesService vacacionesService;  // Declarar el servicio
    private static final int DIAS_TOTALES_VACACIONES = 30; // Días totales anuales

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misvacaciones);

        rvVacaciones = findViewById(R.id.rvVacaciones);
        tvDiasRestantes = findViewById(R.id.tvDiasRestantes);
        etBuscarEmpleado = findViewById(R.id.etBuscarEmpleado);
        btnAgregarVacacion = findViewById(R.id.btnAgregarVacacion);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVolver = findViewById(R.id.btnVolver); // Agregar botón "Volver"

        rvVacaciones.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el presentador
        presenter = new MisVacacionesPresenter(this);

        // Obtener ID del usuario
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        usuarioId = sharedPreferences.getInt("idUsuario", -1);
        isEditable = "Admin".equals(sharedPreferences.getString("tipoEmpleado", "empleado"));

        // Ocultar el campo de búsqueda y el botón si no es Admin
        if (!isEditable) {
            etBuscarEmpleado.setVisibility(View.GONE);
            btnBuscar.setVisibility(View.GONE);
        }

        // Cargar las vacaciones
        presenter.cargarVacaciones(isEditable, usuarioId);

        // Configurar el botón "Volver"
        btnVolver.setOnClickListener(v -> finish());

        // Configurar el botón para agregar vacaciones
        btnAgregarVacacion.setOnClickListener(v -> abrirDialogoFecha());

        // Configurar la búsqueda de vacaciones de otro usuario si es Admin
        if (isEditable) {
            btnBuscar.setOnClickListener(v -> buscarEmpleado());
        }
    }
    // Lógica para buscar vacaciones de otro usuario
    private void buscarEmpleado() {
        String idBuscarString = etBuscarEmpleado.getText().toString().trim();
        if (!idBuscarString.isEmpty()) {
            try {
                int idBuscar = Integer.parseInt(idBuscarString);  // Convertir el ID a entero
                presenter.cargarVacaciones(isEditable, idBuscar);  // Cargar las vacaciones del empleado buscado
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID no válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, introduce un ID para buscar", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirDialogoFecha() {
        // Inicializar calendario para obtener la fecha actual
        final Calendar calendario = Calendar.getInstance();

        // Mostrar un DatePickerDialog para seleccionar la fecha de inicio
        DatePickerDialog fechaInicioDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    final Calendar fechaInicio = Calendar.getInstance();
                    fechaInicio.set(year, month, dayOfMonth);

                    // Mostrar otro DatePickerDialog para seleccionar la fecha de fin
                    DatePickerDialog fechaFinDialog = new DatePickerDialog(
                            this,
                            (view2, year2, month2, dayOfMonth2) -> {
                                final Calendar fechaFin = Calendar.getInstance();
                                fechaFin.set(year2, month2, dayOfMonth2);

                                // Validar que la fecha de fin no sea anterior a la fecha de inicio
                                if (fechaFin.before(fechaInicio)) {
                                    Toast.makeText(this, "La fecha de fin no puede ser anterior a la de inicio", Toast.LENGTH_SHORT).show();
                                } else {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    String fechaInicioStr = sdf.format(fechaInicio.getTime());
                                    String fechaFinStr = sdf.format(fechaFin.getTime());

                                    // Obtener el ID del empleado actual o buscado
                                    String idBuscarEmpleadoStr = etBuscarEmpleado.getText().toString().trim();
                                    int idEmpleado;

                                    // Si no hay búsqueda, asignar el ID del usuario autenticado
                                    if (idBuscarEmpleadoStr.isEmpty()) {
                                        idEmpleado = usuarioId;  // ID del usuario autenticado
                                    } else {
                                        try {
                                            idEmpleado = Integer.parseInt(idBuscarEmpleadoStr);  // ID del empleado buscado
                                        } catch (NumberFormatException e) {
                                            Toast.makeText(this, "ID no válido", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }

                                    // Agregar la vacación usando el presentador
                                    presenter.agregarVacacion(fechaInicioStr, fechaFinStr, idEmpleado);  // Pasar el ID correcto
                                }
                            },
                            calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)
                    );
                    fechaFinDialog.show();  // Mostrar el diálogo de fecha de fin
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        fechaInicioDialog.show();  // Mostrar el diálogo de fecha de inicio
    }


    // Método para calcular los días restantes basados en las vacaciones aprobadas o pendientes
    private int calcularDiasRestantes(List<Vacacion> vacaciones) {
        int diasTomados = 0;
        for (Vacacion vacacion : vacaciones) {
            // Aseguramos que solo las vacaciones aprobadas o pendientes se incluyan en el cálculo
            if (vacacion.isAprobada() || vacacion.isPendiente()) {
                diasTomados += vacacion.getDias();  // Sumamos los días correctamente
            }
        }

        // Evitar valores negativos
        return Math.max(DIAS_TOTALES_VACACIONES - diasTomados, 0);
    }

    @Override
    public void showVacaciones(List<Vacacion> vacaciones) {
        String idBuscarEmpleado = etBuscarEmpleado.getText().toString().trim(); // Obtener el ID de búsqueda
        // Invertir el orden de la lista de vacaciones para mostrar las más recientes primero
        Collections.reverse(vacaciones);
        // Calcular los días restantes y actualizar la vista
        int diasRestantes = calcularDiasRestantes(vacaciones);
        actualizarDiasRestantes(diasRestantes);

        // Pasar el ID buscado al adaptador
        VacacionesAdapter adapter = new VacacionesAdapter(vacaciones, this, isEditable, vacacionesService, diasRestantes, idBuscarEmpleado);
        rvVacaciones.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        // Mostrar un indicador de carga
    }

    @Override
    public void hideLoading() {
        // Ocultar el indicador de carga
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void actualizarDiasRestantes(int diasRestantes) {
        tvDiasRestantes.setText("Días restantes: " + diasRestantes);
    }

    @Override
    public void showVacacionAgregada() {
        Toast.makeText(this, "Vacación agregada correctamente", Toast.LENGTH_SHORT).show();

        // Verificar si hay un ID de empleado buscado
        String idBuscarEmpleadoStr = etBuscarEmpleado.getText().toString().trim();
        if (!idBuscarEmpleadoStr.isEmpty()) {
            try {
                int idEmpleadoBuscado = Integer.parseInt(idBuscarEmpleadoStr);  // Convertir a entero
                presenter.cargarVacaciones(isEditable, idEmpleadoBuscado);  // Recargar las vacaciones del empleado buscado
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID de empleado buscado no válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Si no se ha buscado un empleado, recargar las vacaciones del usuario actual
            presenter.cargarVacaciones(isEditable, usuarioId);
        }
    }


    @Override
    public void onVacacionEdit(Vacacion vacacion) {
        // Implementar la lógica de edición de vacaciones aquí
    }

    @Override
    public void onVacacionView(Vacacion vacacion) {
        // Implementar la lógica de visualización de vacaciones aquí
    }

    @Override
    public void onActualizarDiasRestantes(int diasRestantes) {
        tvDiasRestantes.setText("Días restantes: " + diasRestantes);
    }

    @Override
    public void onVacacionListUpdated() {
        presenter.cargarVacaciones(isEditable, usuarioId);  // Recargar las vacaciones cuando la lista se actualiza
    }
}
