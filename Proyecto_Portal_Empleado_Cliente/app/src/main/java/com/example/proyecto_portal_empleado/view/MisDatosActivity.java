package com.example.proyecto_portal_empleado.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;
import com.example.proyecto_portal_empleado.contracts.ContractMisDatos;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.entities.Usuario;
import com.example.proyecto_portal_empleado.presenters.MisDatosPresenter;
import com.example.proyecto_portal_empleado.utils.MensajeService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisDatosActivity extends AppCompatActivity implements ContractMisDatos.View {

    private TextView tvNombre, tvApellidos, tvEmail, tvCuentaBancaria, tvNumSeguridadSocial, tvPuestoTrabajo, tvDepartamento, tvFechaContratacion, tvTelefonoContacto, tvDireccion, tvFechaNacimiento;
    private EditText etIdBuscar, etNombre, etApellidos, etEmail, etCuentaBancaria, etNumSeguridadSocial, etPuestoTrabajo, etDepartamento, etFechaContratacion, etTelefonoContacto, etDireccion, etFechaNacimiento;
    private Button btnVolver, btnGuardar, btnBuscar;
    private int idUsuario;  // ID del usuario autenticado
    private int idUsuarioActual;  // ID del usuario que se busca y se actualiza
    private String tipoEmpleado; // Tipo de empleado (admin o empleado)
    private TextView userData;

    // Agregar el presentador
    private MisDatosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misdatos);

        // Inicializar el presentador
        presenter = new MisDatosPresenter(this, this);  // El contexto es 'this' ya que estamos en la actividad

        // Obtener el ID y el tipo del usuario autenticado desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", -1);  // Devuelve -1 si no existe el ID
        tipoEmpleado = sharedPreferences.getString("tipoEmpleado", null);  // Asume "empleado" por defecto
        idUsuarioActual = idUsuario;  // Al inicio, el ID actual es el del usuario autenticado

        // Verificar si el ID del usuario es válido
        if (idUsuario == -1) {
            Toast.makeText(this, "Error: No se encontró el ID del usuario", Toast.LENGTH_SHORT).show();
            finish();  // Salir de la actividad si no hay un usuario autenticado
            return;
        }

        bindViews();
        configureListeners();
        btnGuardar.setVisibility(View.GONE);

        // Verificación del tipo de usuario
        if ("Admin".equals(tipoEmpleado)) {
            btnGuardar.setVisibility(View.VISIBLE);
            userData.setVisibility(View.GONE);
            habilitarCampos(true);
            ocultarCampos();
        } else {
            btnGuardar.setVisibility(View.GONE);
            etIdBuscar.setVisibility(View.GONE);
            btnBuscar.setVisibility(View.GONE);
            habilitarCampos(false);
            // Aquí delegamos al presentador la tarea de buscar al usuario
            presenter.fetchUsuario(idUsuario);
        }
    }

    private void configureListeners() {
        btnGuardar.setOnClickListener(v -> {
            String idBuscarString = etIdBuscar.getText().toString().trim();

            if (!idBuscarString.isEmpty()) {
                presenter.fetchUsuarioPorNombre(idBuscarString);  // Realizar la búsqueda de usuario asincrónicamente
            } else {
                Toast.makeText(this, "Por favor, introduce un nombre para buscar", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver.setOnClickListener(v -> finish());

        btnBuscar.setOnClickListener(v -> {
            String idBuscarString = etIdBuscar.getText().toString().trim();
            if (!idBuscarString.isEmpty()) {
                presenter.fetchUsuarioPorNombre(idBuscarString);  // Realizar la búsqueda de usuario asincrónicamente
            } else {
                Toast.makeText(this, "Por favor, introduce un nombre para buscar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Implementación de los métodos de la interfaz ContractMisDatos.View

    @Override
    public void onUsuarioLoaded(Usuario usuario) {
        if (usuario != null) {
            // Llenar los campos con los datos recibidos del usuario
            userData.setVisibility(View.VISIBLE);
            etNombre.setText(usuario.getNombre());
            etApellidos.setText(usuario.getApellidos());
            etEmail.setText(usuario.getEmail());
            etCuentaBancaria.setText(usuario.getCuentaBancaria());
            etNumSeguridadSocial.setText(usuario.getNumSeguridadSocial());
            etDepartamento.setText(usuario.getDepartamento());
            etFechaContratacion.setText(usuario.getFechaContratacion());
            etFechaNacimiento.setText(usuario.getFechaNacimiento());
            etPuestoTrabajo.setText(usuario.getPuestoTrabajo());
            etTelefonoContacto.setText(usuario.getTelefonoContacto());
            etDireccion.setText(usuario.getDireccion());
            mostrarCampos();

            // Configurar el botón Guardar para actualizar los datos del usuario
            btnGuardar.setOnClickListener(v -> {
                Usuario usuarioActualizado = new Usuario(
                        etNombre.getText().toString().trim(),
                        etApellidos.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        etCuentaBancaria.getText().toString().trim(),
                        etNumSeguridadSocial.getText().toString().trim(),
                        etDepartamento.getText().toString().trim(),
                        etFechaContratacion.getText().toString().trim(),
                        etFechaNacimiento.getText().toString().trim(),
                        etTelefonoContacto.getText().toString().trim(),
                        etPuestoTrabajo.getText().toString().trim(),
                        etDireccion.getText().toString().trim()
                );

                // Llamar al presentador para actualizar el usuario con el ID del usuario encontrado
                presenter.actualizarUsuario(usuario.getIdUsuario(), usuarioActualizado);
                String horaActual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());  // Cambiar a "HH:mm:ss"
                String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()); // Cambiar a "dd/MM/yyyy"

                Mensaje mensaje = new Mensaje();
                mensaje.setIdUsuario(usuario.getIdUsuario());
                mensaje.setContenido("Se han modificado tus datos el " + fechaActual + " a las " + horaActual + " por el administrador");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                String fechaActualMensaje = sdf.format(new Date());
                mensaje.setFecha(fechaActualMensaje);
                enviarMensajeNotificacion(mensaje);
            });
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
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
    @Override
    public void onUsuarioUpdated() {
        Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMensajeEnviado() {
        Toast.makeText(this, "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    // Métodos para mostrar y ocultar el indicador de carga
    @Override
    public void showLoading() {
        // Mostrar un indicador de carga, por ejemplo un ProgressBar (debes tener uno en tu layout)
    }

    @Override
    public void hideLoading() {
        // Ocultar el indicador de carga
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    // Otros métodos de la clase (ocultarCampos, mostrarCampos, habilitarCampos, etc.) siguen siendo los mismos.
    private void ocultarCampos() {
        etNombre.setVisibility(View.GONE);
        tvNombre.setVisibility(View.GONE);
        etApellidos.setVisibility(View.GONE);
        tvApellidos.setVisibility(View.GONE);
        etEmail.setVisibility(View.GONE);
        tvEmail.setVisibility(View.GONE);
        etCuentaBancaria.setVisibility(View.GONE);
        tvCuentaBancaria.setVisibility(View.GONE);
        etNumSeguridadSocial.setVisibility(View.GONE);
        tvNumSeguridadSocial.setVisibility(View.GONE);
        etPuestoTrabajo.setVisibility(View.GONE);
        tvPuestoTrabajo.setVisibility(View.GONE);
        etDepartamento.setVisibility(View.GONE);
        tvDepartamento.setVisibility(View.GONE);
        etFechaContratacion.setVisibility(View.GONE);
        tvFechaContratacion.setVisibility(View.GONE);
        etTelefonoContacto.setVisibility(View.GONE);
        tvTelefonoContacto.setVisibility(View.GONE);
        etDireccion.setVisibility(View.GONE);
        tvDireccion.setVisibility(View.GONE);
        etFechaNacimiento.setVisibility(View.GONE);
        tvFechaNacimiento.setVisibility(View.GONE);
    }

    private void mostrarCampos() {
        etNombre.setVisibility(View.VISIBLE);
        tvNombre.setVisibility(View.VISIBLE);
        etApellidos.setVisibility(View.VISIBLE);
        tvApellidos.setVisibility(View.VISIBLE);
        etEmail.setVisibility(View.VISIBLE);
        tvEmail.setVisibility(View.VISIBLE);
        etCuentaBancaria.setVisibility(View.VISIBLE);
        tvCuentaBancaria.setVisibility(View.VISIBLE);
        etNumSeguridadSocial.setVisibility(View.VISIBLE);
        tvNumSeguridadSocial.setVisibility(View.VISIBLE);
        etPuestoTrabajo.setVisibility(View.VISIBLE);
        tvPuestoTrabajo.setVisibility(View.VISIBLE);
        etDepartamento.setVisibility(View.VISIBLE);
        tvDepartamento.setVisibility(View.VISIBLE);
        etFechaContratacion.setVisibility(View.VISIBLE);
        tvFechaContratacion.setVisibility(View.VISIBLE);
        etTelefonoContacto.setVisibility(View.VISIBLE);
        tvTelefonoContacto.setVisibility(View.VISIBLE);
        etDireccion.setVisibility(View.VISIBLE);
        tvDireccion.setVisibility(View.VISIBLE);
        etFechaNacimiento.setVisibility(View.VISIBLE);
        tvFechaNacimiento.setVisibility(View.VISIBLE);
    }

    private void habilitarCampos(boolean habilitar) {
        EditText[] editTexts = {
                etNombre, etApellidos, etEmail, etCuentaBancaria, etNumSeguridadSocial,
                etDireccion, etDepartamento, etFechaNacimiento, etFechaContratacion,
                etPuestoTrabajo, etTelefonoContacto
        };

        for (EditText editText : editTexts) {
            editText.setEnabled(habilitar);
            editText.setFocusable(habilitar);
            editText.setFocusableInTouchMode(habilitar);
            editText.setClickable(habilitar);
            editText.setCursorVisible(habilitar);  // Ocultar el cursor si no está habilitado
        }
    }

    private void bindViews() {
        userData = findViewById(R.id.tvUserData);
        etIdBuscar = findViewById(R.id.etBuscarEmpleado);  // EditText para buscar por ID
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEmail = findViewById(R.id.etEmail);
        etCuentaBancaria = findViewById(R.id.etCuentaBancaria);
        etNumSeguridadSocial = findViewById(R.id.etNumSeguridadSocial);
        btnGuardar = findViewById(R.id.btnGuardarCambios);
        etPuestoTrabajo = findViewById(R.id.etPuestoTrabajo);
        etDepartamento = findViewById(R.id.etDepartamento);
        etFechaContratacion = findViewById(R.id.etFechaContratacion);
        etTelefonoContacto = findViewById(R.id.etTelefonoContacto);
        etDireccion = findViewById(R.id.etDireccion);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        btnVolver = findViewById(R.id.btnVolver);
        btnBuscar = findViewById(R.id.btnBuscar);

        // TextView asociados con los EditText
        tvNombre = findViewById(R.id.tvNombre);
        tvApellidos = findViewById(R.id.tvApellidos);
        tvEmail = findViewById(R.id.tvEmail);
        tvCuentaBancaria = findViewById(R.id.tvCuentaBancaria);
        tvNumSeguridadSocial = findViewById(R.id.tvNumSeguridadSocial);
        tvPuestoTrabajo = findViewById(R.id.tvPuestoTrabajo);
        tvDepartamento = findViewById(R.id.tvDepartamento);
        tvFechaContratacion = findViewById(R.id.tvFechaContratacion);
        tvTelefonoContacto = findViewById(R.id.tvTelefonoContacto);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento);
    }
}
