package com.example.proyecto_portal_empleado.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.adapters.HistorialFichajesAdapter;
import com.example.proyecto_portal_empleado.contracts.ContractHistorialFichajes;
import com.example.proyecto_portal_empleado.entities.Fichaje;
import com.example.proyecto_portal_empleado.entities.DiaTrabajo;
import com.example.proyecto_portal_empleado.presenters.HistorialFichajesPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class HistorialFichajeActivity extends AppCompatActivity implements ContractHistorialFichajes.View {

    private RecyclerView rvHistorial;
    private HistorialFichajesAdapter historialAdapter;
    private CalendarView calendarView;
    private int usuarioActual;
    private Button btnVolver;
    private LinearLayout searchBarLayout;
    private EditText etBuscarEmpleado;
    private Button btnBuscar;
    private boolean isAdmin;
    private String fechaSeleccionada;
    private HistorialFichajesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_fichajes);

        // Inicializar vistas
        btnVolver = findViewById(R.id.btnVolver);
        rvHistorial = findViewById(R.id.rvHistorial);
        calendarView = findViewById(R.id.calendarView);
        searchBarLayout = findViewById(R.id.searchBarLayout);
        etBuscarEmpleado = findViewById(R.id.etBuscarEmpleado);
        btnBuscar = findViewById(R.id.btnBuscar);

        // Configurar RecyclerView
        rvHistorial.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar presenter
        presenter = new HistorialFichajesPresenter(this);

        // Obtener ID del usuario actual
        usuarioActual = obtenerUsuarioId();

        // Verificar si el usuario es admin
        isAdmin = checkIfUserIsAdmin();

        // Mostrar la barra de búsqueda solo si es admin
        if (isAdmin) {
            searchBarLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Por favor, busque un empleado para ver los fichajes.", Toast.LENGTH_SHORT).show();
        } else {
            // Cargar historial inicial (para el día actual)
            fechaSeleccionada = obtenerFechaActual();
            presenter.cargarHistorialFichajes(usuarioActual);
        }

        // Listener para cambios de fecha en el calendario
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
            Log.d("HistorialActivity", "Fecha seleccionada en el calendario: " + fechaSeleccionada);

            // Si es admin y ha buscado un empleado, cargar sus fichajes automáticamente
            if (isAdmin && usuarioActual != -1) {
                presenter.cargarHistorialFichajes(usuarioActual);
            } else if (!isAdmin) {
                presenter.cargarHistorialFichajes(usuarioActual);
            }
        });

        // Listener para botón Volver
        btnVolver.setOnClickListener(v -> finish());

        // Listener para botón Buscar (solo si es admin)
        btnBuscar.setOnClickListener(v -> {
            String usuarioIdStr = etBuscarEmpleado.getText().toString().trim();
            if (!usuarioIdStr.isEmpty()) {
                int usuarioId = Integer.parseInt(usuarioIdStr);
                usuarioActual = usuarioId;  // Guardar el ID del empleado buscado

                // Cargar los fichajes del empleado para la fecha seleccionada
                presenter.cargarHistorialFichajes(usuarioActual);

                // Forzar la actualización de los fichajes con la fecha seleccionada (incluso si es el día actual)
                fechaSeleccionada = obtenerFechaActual();
                presenter.cargarHistorialFichajes(usuarioActual);
            } else {
                Toast.makeText(HistorialFichajeActivity.this, "Por favor, ingrese un ID de empleado válido", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void showHistorial(List<Fichaje> fichajes) {
        // Verificar si el usuarioActual es correcto
        Log.d("HistorialActivity", "ID del usuario actual: " + usuarioActual);

        if (fichajes != null && !fichajes.isEmpty()) {
            // Filtrar los fichajes del día seleccionado
            List<Fichaje> fichajesDelDia = filtrarFichajesPorFecha(fichajes, fechaSeleccionada);
            if (!fichajesDelDia.isEmpty()) {
                List<DiaTrabajo> diasTrabajo = procesarFichajesDelDia(fichajesDelDia);
                Collections.reverse(diasTrabajo);  // Invertir la lista para mostrar las entradas más recientes primero
                historialAdapter = new HistorialFichajesAdapter(diasTrabajo);
                rvHistorial.setAdapter(historialAdapter);
            } else {
                Log.d("HistorialActivity", "No hay fichajes para la fecha: " + fechaSeleccionada);
                Toast.makeText(this, "No hay fichajes para esta fecha", Toast.LENGTH_SHORT).show();
                rvHistorial.setAdapter(null); // Limpiar el adaptador si no hay fichajes
            }
        } else {
            Log.d("HistorialActivity", "No hay fichajes para el usuario: " + usuarioActual);
            Toast.makeText(this, "No hay fichajes para mostrar", Toast.LENGTH_SHORT).show();
            rvHistorial.setAdapter(null);
        }
    }


    // Método para obtener la fecha actual
    private String obtenerFechaActual() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    // Método para obtener el ID del usuario actual desde SharedPreferences
    private int obtenerUsuarioId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt("idUsuario", -1);
        Log.d("HistorialActivity", "Obtenido idUsuario: " + idUsuario);
        return idUsuario;
    }

    // Método para verificar si el usuario actual es admin
    private boolean checkIfUserIsAdmin() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String userType = sharedPreferences.getString("tipoEmpleado", "empleado");
        return "Admin".equalsIgnoreCase(userType);
    }
    private List<Fichaje> filtrarFichajesPorFecha(List<Fichaje> fichajes, String fechaSeleccionada) {
        List<Fichaje> fichajesDelDia = new ArrayList<>();

        if (fechaSeleccionada == null || fechaSeleccionada.isEmpty()) {
            Log.e("HistorialActivity", "Fecha seleccionada es nula o vacía.");
            return fichajesDelDia;  // Devolver una lista vacía si la fecha es nula o vacía
        }

        SimpleDateFormat sdfSelected = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdfFichajeOld = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()); // Formato antiguo
        SimpleDateFormat sdfFichajeNew = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());  // Formato nuevo

        try {
            Date selectedDate = sdfSelected.parse(fechaSeleccionada);

            for (Fichaje fichaje : fichajes) {
                if (fichaje.getFecha() == null || fichaje.getFecha().isEmpty()) {
                    Log.e("HistorialActivity", "Fichaje con fecha nula o vacía, ignorando...");
                    continue;  // Saltar fichajes con fechas nulas o vacías
                }

                Date fichajeDate = null;

                // Intentamos con el formato nuevo
                try {
                    fichajeDate = sdfFichajeNew.parse(fichaje.getFecha());
                } catch (ParseException e) {
                    // Si falla, intentamos con el formato antiguo
                    try {
                        fichajeDate = sdfFichajeOld.parse(fichaje.getFecha());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }

                if (fichajeDate != null && isSameDay(selectedDate, fichajeDate)) {
                    fichajesDelDia.add(fichaje);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fichajesDelDia;
    }




    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }


    // Método para procesar los fichajes del día en objetos DiaTrabajo
    private List<DiaTrabajo> procesarFichajesDelDia(List<Fichaje> fichajesDelDia) {
        List<DiaTrabajo> diasTrabajo = new ArrayList<>();
        String entradaHoraStr = null;

        for (Fichaje fichaje : fichajesDelDia) {
            String tipo = fichaje.getTipo();
            String hora = fichaje.getHora();
            String tiempoTotalJornada = fichaje.getTiempoTotalJornada();

            if ("Entrada".equalsIgnoreCase(tipo)) {
                entradaHoraStr = hora;
            } else if ("Salida".equalsIgnoreCase(tipo) && entradaHoraStr != null) {
                DiaTrabajo diaTrabajo = new DiaTrabajo(
                        fichaje.getFecha(),
                        entradaHoraStr,
                        hora,
                        tiempoTotalJornada != null ? tiempoTotalJornada : "N/A"
                );
                diasTrabajo.add(diaTrabajo);
                entradaHoraStr = null;
            }
        }

        // Si hay una entrada sin salida, crear DiaTrabajo con hora de salida "N/A"
        if (entradaHoraStr != null) {
            diasTrabajo.add(new DiaTrabajo(fichajesDelDia.get(0).getFecha(), entradaHoraStr, "N/A", "N/A"));
        }

        return diasTrabajo;
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
}
