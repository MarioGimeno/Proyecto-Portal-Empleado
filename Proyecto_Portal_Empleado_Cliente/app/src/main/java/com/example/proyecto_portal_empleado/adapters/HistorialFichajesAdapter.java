package com.example.proyecto_portal_empleado.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.entities.DiaTrabajo;
import java.util.List;

public class HistorialFichajesAdapter extends RecyclerView.Adapter<HistorialFichajesAdapter.ViewHolder> {

    private List<DiaTrabajo> listaDiasTrabajo;

    public HistorialFichajesAdapter(List<DiaTrabajo> listaDiasTrabajo) {
        this.listaDiasTrabajo = listaDiasTrabajo;
    }

    @NonNull
    @Override
    public HistorialFichajesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each DiaTrabajo
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dia_trabajo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialFichajesAdapter.ViewHolder holder, int position) {
        DiaTrabajo diaTrabajo = listaDiasTrabajo.get(position);

        holder.tvFecha.setText("Fecha: " + diaTrabajo.getFecha());
        holder.tvHoraEntrada.setText("Hora de Entrada: " + (diaTrabajo.getHoraEntrada() != null ? diaTrabajo.getHoraEntrada() : "N/A"));
        holder.tvHoraSalida.setText("Hora de Salida: " + (diaTrabajo.getHoraSalida() != null ? diaTrabajo.getHoraSalida() : "N/A"));
        holder.tvHorasTotales.setText("Horas Totales: " + (diaTrabajo.getHorasTotales() != null ? diaTrabajo.getHorasTotales() : "N/A"));
    }


    @Override
    public int getItemCount() {
        return listaDiasTrabajo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvHoraEntrada, tvHoraSalida, tvHorasTotales;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHoraEntrada = itemView.findViewById(R.id.tvHoraEntrada);
            tvHoraSalida = itemView.findViewById(R.id.tvHoraSalida);
            tvHorasTotales = itemView.findViewById(R.id.tvHorasTotales);
        }
    }
}
