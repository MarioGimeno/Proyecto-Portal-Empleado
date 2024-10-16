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
import com.example.proyecto_portal_empleado.contracts.ContractMisDatos;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.entities.Usuario;
import com.example.proyecto_portal_empleado.presenters.MisDatosPresenter;

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
            // Captura los datos del usuario y los pasa al presentador para actualizarlos
            Usuario usuario = new Usuario(
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
            presenter.actualizarUsuario(idUsuarioActual, usuario);  // Llamada al presentador para actualizar
        });

        btnVolver.setOnClickListener(v -> finish());

        btnBuscar.setOnClickListener(v -> {
            // Obtener el ID del usuario a buscar y solicitar al presentador que lo busque
            String idBuscarString = etIdBuscar.getText().toString().trim();
            if (!idBuscarString.isEmpty()) {
                try {
                    int idBuscar = Integer.parseInt(idBuscarString);
                    presenter.fetchUsuario(idBuscar);
                    idUsuarioActual = idBuscar;
                } catch (NumberFormatException e) {
                    Toast.makeText(MisDatosActivity.this, "ID no válido. Introduce un número", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MisDatosActivity.this, "Por favor, introduce un ID para buscar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Implementación de los métodos de la interfaz ContractMisDatos.View

    @Override
    public void onUsuarioLoaded(Usuario usuario) {
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
