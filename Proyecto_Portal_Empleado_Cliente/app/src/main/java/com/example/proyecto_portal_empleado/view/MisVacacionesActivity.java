package com.example.proyecto_portal_empleado.view;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.proyecto_portal_empleado.connection.RetrofitClient;
import com.example.proyecto_portal_empleado.contracts.ContractMisDatos;
import com.example.proyecto_portal_empleado.contracts.ContractMisVacaciones;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.entities.Vacacion;
import com.example.proyecto_portal_empleado.presenters.MisDatosPresenter;
import com.example.proyecto_portal_empleado.presenters.MisVacacionesPresenter;
import com.example.proyecto_portal_empleado.utils.MensajeService;
import com.example.proyecto_portal_empleado.utils.VacacionesService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private MisDatosPresenter misDatosPresenter; // Nueva instancia para obtener el ID del usuario por nombre
    private int diasRestantes = 30;
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
        String nombreBuscar = etBuscarEmpleado.getText().toString().trim();
        if (!nombreBuscar.isEmpty()) {
            presenter.cargarVacacionesPorNombre(isEditable, nombreBuscar);  // Llama al presenter con el nombre en lugar del ID
        } else {
            Toast.makeText(this, "Por favor, introduce un nombre para buscar", Toast.LENGTH_SHORT).show();
        }
    }



    private void abrirDialogoFecha() {
        final Calendar calendario = Calendar.getInstance();

        DatePickerDialog fechaInicioDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    final Calendar fechaInicio = Calendar.getInstance();
                    fechaInicio.set(year, month, dayOfMonth);

                    DatePickerDialog fechaFinDialog = new DatePickerDialog(
                            this,
                            (view2, year2, month2, dayOfMonth2) -> {
                                final Calendar fechaFin = Calendar.getInstance();
                                fechaFin.set(year2, month2, dayOfMonth2);

                                if (fechaFin.before(fechaInicio)) {
                                    Toast.makeText(this, "La fecha de fin no puede ser anterior a la de inicio", Toast.LENGTH_SHORT).show();
                                } else {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    String fechaInicioStr = sdf.format(fechaInicio.getTime());
                                    String fechaFinStr = sdf.format(fechaFin.getTime());

                                    // Calcular la cantidad de días de la vacación
                                    long diffInMillis = fechaFin.getTimeInMillis() - fechaInicio.getTimeInMillis();
                                    int diasVacacion = (int) (diffInMillis / (1000 * 60 * 60 * 24)) + 1;

                                    Log.d("Dias restantes", "Dias restantes = " + diasRestantes);
                                    // Verifica si los días restantes son suficientes
                                    if (diasRestantes < diasVacacion) {
                                        Toast.makeText(this, "No tienes suficientes días restantes para esta vacación", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    String nombreBuscarEmpleado = etBuscarEmpleado.getText().toString().trim();

                                    if (nombreBuscarEmpleado.isEmpty()) {
                                        // Si no se ha introducido nombre, usa el usuario autenticado
                                        presenter.agregarVacacion(fechaInicioStr, fechaFinStr, usuarioId);
                                        String horaActual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                                        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                                        Mensaje mensaje = new Mensaje();
                                        mensaje.setIdUsuario(usuarioId);
                                        mensaje.setContenido("Se ha añadido una vacación el " + fechaActual + " a las " + horaActual);

                                        String fechaActualMensaje = sdf.format(new Date());
                                        mensaje.setFecha(fechaActualMensaje);
                                        enviarMensajeNotificacion(mensaje);
                                    } else {
                                        presenter.agregarVacacionPorNombre(fechaInicioStr, fechaFinStr, nombreBuscarEmpleado);
                                        String horaActual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                                        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                                        Mensaje mensaje = new Mensaje();
                                        mensaje.setIdUsuario(usuarioId);
                                        mensaje.setContenido("Se ha añadido una vacación el " + fechaActual + " a las " + horaActual);

                                        String fechaActualMensaje = sdf.format(new Date());
                                        mensaje.setFecha(fechaActualMensaje);
                                        enviarMensajeNotificacion(mensaje);
                                    }
                                }
                            },
                            calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)
                    );
                    fechaFinDialog.show();
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        fechaInicioDialog.show();
    }

    private void enviarMensajeNotificacion(Mensaje mensaje) {
        MensajeService mensajeService = RetrofitClient.getRetrofitInstance().create(MensajeService.class);
        Log.d("MensajeNotificacion", "Mensaje enviado: " + mensaje.toString()); // Log para comprobar los datos enviados
        mensajeService.enviarMensaje(mensaje).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Mensaje", "Mensaje enviado con éxito.");
                } else {
                    try {
                        // Registrar el código de respuesta y convertir el cuerpo de la respuesta a String
                        String errorBody = response.errorBody().string();
                        Log.e("Mensaje", "Error al enviar el mensaje. Código: " + response.code());
                        Log.e("Mensaje", "Cuerpo de la respuesta: " + errorBody);
                    } catch (IOException e) {
                        Log.e("Mensaje", "Error al leer el cuerpo de la respuesta", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Mensaje", "Error en la conexión", t);
            }
        });
    }
    // Método para calcular los días restantes basados en las vacaciones aprobadas o pendientes
    private int calcularDiasRestantes(List<Vacacion> vacaciones) {
        int diasTomados = 0;
        for (Vacacion vacacion : vacaciones) {
            if (vacacion.isAprobada() || vacacion.isPendiente()) {
                diasTomados += vacacion.getDias();
            }
        }
        // Calcula los días restantes
        int diasRestantesCalculado = Math.max(DIAS_TOTALES_VACACIONES - diasTomados, 0);
        Log.d("CalculoDias", "Días Tomados: " + diasTomados + ", Días Restantes Calculados: " + diasRestantesCalculado);
        return diasRestantesCalculado;
    }


    @Override
    public void showVacaciones(List<Vacacion> vacaciones) {
        Collections.reverse(vacaciones);

        diasRestantes = calcularDiasRestantes(vacaciones); // Calcula los días restantes
        actualizarDiasRestantes(diasRestantes);  // Actualiza el TextView con los días restantes

        Log.d("ShowVacaciones", "Días Restantes en TextView: " + diasRestantes); // Confirma que se esté pasando correctamente

        String usuarioIdString = String.valueOf(usuarioId + 1);
        VacacionesAdapter adapter = new VacacionesAdapter(vacaciones, this, isEditable, vacacionesService, diasRestantes, usuarioIdString);
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
                Log.d("Error", "Error de vacacion");
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
