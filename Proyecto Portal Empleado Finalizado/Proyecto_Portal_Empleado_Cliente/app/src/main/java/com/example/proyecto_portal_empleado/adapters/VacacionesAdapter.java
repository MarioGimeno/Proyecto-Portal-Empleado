package com.example.proyecto_portal_empleado.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.connection.RetrofitClient;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.entities.Vacacion;
import com.example.proyecto_portal_empleado.utils.MensajeService;
import com.example.proyecto_portal_empleado.utils.VacacionesService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacacionesAdapter extends RecyclerView.Adapter<VacacionesAdapter.VacacionViewHolder> {

    private List<Vacacion> vacacionesList;
    private OnVacacionInteractionListener listener;
    private boolean isEditable;
    private VacacionesService vacacionesService;
    private int diasRestantes;
    private String idBuscarEmpleado; // This will store the value passed from the activity

    // Update the constructor to accept idBuscarEmpleado
    public VacacionesAdapter(List<Vacacion> vacacionesList, OnVacacionInteractionListener listener, boolean isEditable, VacacionesService vacacionesService, int diasRestantes, String idBuscarEmpleado) {
        this.vacacionesList = vacacionesList;
        this.listener = listener;
        this.isEditable = isEditable;
        this.diasRestantes = diasRestantes;
        this.idBuscarEmpleado = idBuscarEmpleado;  // Store the passed value

        // Inicializar vacacionesService si es nulo
        if (vacacionesService == null) {
            this.vacacionesService = RetrofitClient.getRetrofitInstance().create(VacacionesService.class);
        } else {
            this.vacacionesService = vacacionesService;
        }
    }

    @NonNull
    @Override
    public VacacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacacion_item, parent, false);
        return new VacacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacacionViewHolder holder, int position) {
        Vacacion vacacion = vacacionesList.get(position);

        holder.tvFechaInicio.setText("Inicio: " + vacacion.getFechaInicio());
        holder.tvFechaFin.setText("Fin: " + vacacion.getFechaFin());

        Log.d("VacacionesAdapter", "isEditable en adapter: " + isEditable);

        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String tipoEmpleado = sharedPreferences.getString("tipoEmpleado", "empleado"); // Por defecto, "empleado"

        isEditable = "Admin".equals(tipoEmpleado);
        Log.d("VacacionesAdapter", "Tipo de empleado: " + tipoEmpleado + " - isEditable: " + isEditable);

        if (isEditable) {
            holder.btnAprobar.setVisibility(View.VISIBLE);
            holder.btnRechazar.setVisibility(View.VISIBLE);
        } else {
            holder.btnAprobar.setVisibility(View.GONE);
            holder.btnRechazar.setVisibility(View.GONE);
        }

        holder.btnAprobar.setOnClickListener(v -> {
            aprobarVacacion(vacacion, holder, vacacion.getDias(), holder.itemView.getContext());  // Pasar el contexto al aprobarVacacion
        });

        holder.btnRechazar.setOnClickListener(v -> {
            rechazarVacacion(vacacion, holder, vacacion.getDias(), holder.itemView.getContext());
        });

        if (vacacion.isPendiente()) {
            holder.ivEstadoVacacion.setImageResource(R.drawable.ic_pending); // Icono de pendiente
        } else if (vacacion.isAprobada()) {
            holder.ivEstadoVacacion.setImageResource(R.drawable.ic_approved); // Icono de aprobado
        } else {
            holder.ivEstadoVacacion.setImageResource(R.drawable.ic_rejected); // Icono de rechazado
        }
    }

    @Override
    public int getItemCount() {
        return vacacionesList.size();
    }

    // ViewHolder
    public static class VacacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvFechaInicio, tvFechaFin;
        ImageView ivEstadoVacacion;
        Button btnAprobar, btnRechazar;

        public VacacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            ivEstadoVacacion = itemView.findViewById(R.id.ivEstadoVacacion);
            btnAprobar = itemView.findViewById(R.id.btnAprobar);
            btnRechazar = itemView.findViewById(R.id.btnRechazar);
        }
    }

    // Método para aprobar la vacación y actualizar los días restantes
    private void aprobarVacacion(Vacacion vacacion, VacacionViewHolder holder, int diasVacacion, Context context) {
        if (!idBuscarEmpleado.isEmpty()) {
            int usuarioId;
            try {
                usuarioId = Integer.parseInt(idBuscarEmpleado); // Use idBuscarEmpleado

                vacacionesService.aprobarVacacion(vacacion.getIdVacacion()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(holder.itemView.getContext(), "Vacación aprobada", Toast.LENGTH_SHORT).show();

                            // Marcar la vacación como aprobada, sin descontar los días nuevamente
                            vacacion.setPendiente(false);
                            vacacion.setAprobada(true);

                            notifyDataSetChanged();  // Actualizar la lista en la UI

                            // Enviar una notificación al empleado
                            Mensaje mensaje = new Mensaje();
                            mensaje.setIdUsuario(usuarioId); // Use usuarioId from the EditText
                            mensaje.setContenido("El admin ha aprobado tu vacación desde " + vacacion.getFechaInicio() + " hasta " + vacacion.getFechaFin());

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String fechaActualMensaje = sdf.format(new Date());
                            mensaje.setFecha(fechaActualMensaje);
                            enviarMensajeNotificacion(mensaje);
                        } else {
                            Toast.makeText(holder.itemView.getContext(), "Error al aprobar la vacación", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(holder.itemView.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Por favor, introduce un ID válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Por favor, introduce un ID", Toast.LENGTH_SHORT).show();
        }
    }


    // Método para rechazar la vacación y actualizar los días restantes
    private void rechazarVacacion(Vacacion vacacion, VacacionViewHolder holder, int diasVacacion, Context context) {
        if (!idBuscarEmpleado.isEmpty()) {
            int usuarioId;
            try {
                usuarioId = Integer.parseInt(idBuscarEmpleado); // Use idBuscarEmpleado

                vacacionesService.rechazarVacacion(vacacion.getIdVacacion()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            holder.ivEstadoVacacion.setImageResource(R.drawable.ic_rejected); // Cambia el ícono a rechazado
                            vacacion.setPendiente(false);  // Marcar como no pendiente
                            vacacion.setAprobada(false);   // Marcar como no aprobada

                            // Actualizar los días restantes después de rechazar
                            diasRestantes += diasVacacion;
                            listener.onActualizarDiasRestantes(diasRestantes);  // Notificar al listener
                            notifyDataSetChanged(); // Actualizar la lista en la UI

                            Mensaje mensaje = new Mensaje();
                            mensaje.setIdUsuario(usuarioId); // Use usuarioId from the EditText
                            mensaje.setContenido("El admin ha rechazado tu vacación desde " + vacacion.getFechaInicio() + " hasta " + vacacion.getFechaFin());

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String fechaActualMensaje = sdf.format(new Date());
                            mensaje.setFecha(fechaActualMensaje);

                            enviarMensajeNotificacion(mensaje);
                        } else {
                            Toast.makeText(holder.itemView.getContext(), "Error al rechazar la vacación", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(holder.itemView.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Por favor, introduce un ID válido", Toast.LENGTH_SHORT).show();
                Log.e("Error", "ID ingresado no es válido: " + e.getMessage());
            }
        } else {
            Toast.makeText(context, "Por favor, introduce un ID", Toast.LENGTH_SHORT).show();
        }
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

    public interface OnVacacionInteractionListener {
        void onVacacionEdit(Vacacion vacacion);
        void onVacacionView(Vacacion vacacion);
        void onActualizarDiasRestantes(int diasRestantes);
        void onVacacionListUpdated();
    }
}
