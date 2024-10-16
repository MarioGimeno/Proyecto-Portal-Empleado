package com.example.proyecto_portal_empleado.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;
import com.example.proyecto_portal_empleado.contracts.ContractFichajes;
import com.example.proyecto_portal_empleado.entities.Fichaje;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.presenters.FichajesPresenter;
import com.example.proyecto_portal_empleado.utils.MensajeService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichajeActivity extends AppCompatActivity implements ContractFichajes.View {

    private TextView tvFechaHoraActual;
    private Button btnFicharEntrada, btnFicharSalida, btnVerHistorial, btnVolver;
    private int usuarioActual; // Usuario que está fichando
    private FichajesPresenter presenter;
    private long tiempoInicial;
    private boolean isRunning = false; // Flag para controlar el hilo del contador
    private Thread contadorThread; // Referencia al hilo del contador
    private boolean haySesionIniciada = false; // Variable para controlar si hay una sesión iniciada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichajes);

        // Inicializar vistas
        tvFechaHoraActual = findViewById(R.id.tvFechaHoraActual);
        btnFicharEntrada = findViewById(R.id.btnFicharEntrada);
        btnFicharSalida = findViewById(R.id.btnFicharSalida);
        btnVerHistorial = findViewById(R.id.btnVerHistorial);
        btnVolver = findViewById(R.id.btnVolver);

        // Inicializar el presenter
        presenter = new FichajesPresenter(this);

        // Obtener el ID del usuario
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        usuarioActual = sharedPreferences.getInt("idUsuario", -1);

        // Configurar los botones
        btnFicharEntrada.setOnClickListener(v -> ficharEntrada());
        btnFicharSalida.setOnClickListener(v -> ficharSalida());
        btnVerHistorial.setOnClickListener(v -> abrirHistorial());
        btnVolver.setOnClickListener(v -> finish());

        actualizarEstadoBotones();
    }

    private void actualizarEstadoBotones() {
        SharedPreferences sharedPreferences = getSharedPreferences("FichajePreferences", MODE_PRIVATE);
        long tiempoEntrada = sharedPreferences.getLong("tiempoEntrada", -1);

        if (tiempoEntrada != -1) {
            // Hay una sesión iniciada
            haySesionIniciada = true;
            btnFicharEntrada.setEnabled(false);
            btnFicharSalida.setEnabled(true);
            iniciarContadorDesdeTiempo(tiempoEntrada);
        } else {
            haySesionIniciada = false;
            btnFicharEntrada.setEnabled(true);
            btnFicharSalida.setEnabled(false);
            tvFechaHoraActual.setText("00:00:00");
        }
    }

    private void ficharEntrada() {
        long tiempoEntrada = System.currentTimeMillis();
        String horaActual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());  // Cambiar a "HH:mm:ss"
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()); // Cambiar a "dd/MM/yyyy"

        SharedPreferences sharedPreferences = getSharedPreferences("FichajePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("tiempoEntrada", tiempoEntrada);
        editor.apply();

        // Crear el objeto FichajeEntrada
        Fichaje fichajeEntrada = new Fichaje("Entrada", horaActual, fechaActual, usuarioActual);
        // Enviar mensaje de notificación
        Mensaje mensaje = new Mensaje();
        mensaje.setIdUsuario(usuarioActual);
        mensaje.setContenido("Has registrado tu entrada el " + fechaActual + " a las " + horaActual);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActualMensaje = sdf.format(new Date());
        mensaje.setFecha(fechaActualMensaje);
        enviarMensajeNotificacion(mensaje);
        // Usar el objeto Fichaje para agregar fichaje
        presenter.agregarFichaje(fichajeEntrada);

        iniciarContador();
        btnFicharEntrada.setEnabled(false);
        btnFicharSalida.setEnabled(true);
        haySesionIniciada = true;
    }

    private void ficharSalida() {
        if (!haySesionIniciada) {
            Toast.makeText(this, "No hay una sesión iniciada", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("FichajePreferences", MODE_PRIVATE);
        long tiempoEntrada = sharedPreferences.getLong("tiempoEntrada", -1);
        long tiempoSalida = System.currentTimeMillis();
        long tiempoTotalJornadaMillis = tiempoSalida - tiempoEntrada;

        String tiempoTotalJornada = formatMillisecondsToHHMMSS(tiempoTotalJornadaMillis);
        String horaActual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());  // Cambiar a "HH:mm:ss"
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()); // Cambiar a "dd/MM/yyyy"

        // Crear el objeto FichajeSalida con el tiempo total de la jornada
        Fichaje fichajeSalida = new Fichaje("Salida", horaActual, fechaActual, usuarioActual, tiempoTotalJornada);

        // Enviar mensaje de notificación
        Mensaje mensaje = new Mensaje();
        mensaje.setIdUsuario(usuarioActual);
        mensaje.setContenido("Has registrado tu salida el " + fechaActual + " a las " + horaActual + ". Jornada: " + tiempoTotalJornada);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaActualMensaje = sdf.format(new Date());
        mensaje.setFecha(fechaActualMensaje);
        enviarMensajeNotificacion(mensaje);
        // Usar el objeto Fichaje para agregar fichaje
        presenter.agregarFichaje(fichajeSalida);

        detenerContador();
        tvFechaHoraActual.setText("00:00:00");
        btnFicharEntrada.setEnabled(true);
        btnFicharSalida.setEnabled(false);
        haySesionIniciada = false;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("tiempoEntrada");
        editor.apply();
    }

    private String formatMillisecondsToHHMMSS(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void iniciarContador() {
        tiempoInicial = System.currentTimeMillis();
        isRunning = true;

        contadorThread = new Thread(() -> {
            while (isRunning) {
                long tiempoActual = System.currentTimeMillis();
                long tiempoTranscurrido = tiempoActual - tiempoInicial;

                long segundos = (tiempoTranscurrido / 1000) % 60;
                long minutos = (tiempoTranscurrido / (1000 * 60)) % 60;
                long horas = (tiempoTranscurrido / (1000 * 60 * 60));

                runOnUiThread(() -> tvFechaHoraActual.setText(
                        String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segundos)
                ));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        contadorThread.start();
    }

    private void iniciarContadorDesdeTiempo(long tiempoEntrada) {
        isRunning = true;

        contadorThread = new Thread(() -> {
            while (isRunning) {
                long tiempoActual = System.currentTimeMillis();
                long tiempoTranscurrido = tiempoActual - tiempoEntrada;

                long segundos = (tiempoTranscurrido / 1000) % 60;
                long minutos = (tiempoTranscurrido / (1000 * 60)) % 60;
                long horas = (tiempoTranscurrido / (1000 * 60 * 60));

                runOnUiThread(() -> tvFechaHoraActual.setText(
                        String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segundos)
                ));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        contadorThread.start();
    }
    // Método para enviar el mensaje de notificación
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
    private void detenerContador() {
        isRunning = false;
        if (contadorThread != null) {
            try {
                contadorThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void abrirHistorial() {
        Intent intent = new Intent(FichajeActivity.this, HistorialFichajeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFichajes(List<Fichaje> fichajes) {
        // Mostrar los fichajes
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
    public void showFichajeAgregado() {
        Toast.makeText(this, "Fichaje agregado correctamente", Toast.LENGTH_SHORT).show();
        presenter.cargarFichajes(usuarioActual); // Recargar los fichajes
    }
}
