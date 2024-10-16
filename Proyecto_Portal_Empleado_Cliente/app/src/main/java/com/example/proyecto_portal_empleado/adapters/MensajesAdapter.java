package com.example.proyecto_portal_empleado.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import java.util.List;

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.MensajeViewHolder> {

    private List<Mensaje> mensajesList;

    public MensajesAdapter(List<Mensaje> mensajesList) {
        this.mensajesList = mensajesList;
    }

    @NonNull
    @Override
    public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false);
        return new MensajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeViewHolder holder, int position) {
        Mensaje mensaje = mensajesList.get(position);
        holder.tvContenido.setText(mensaje.getContenido());
        holder.tvFecha.setText(mensaje.getFecha());
    }

    @Override
    public int getItemCount() {
        return mensajesList.size();
    }

    public void setMensajesList(List<Mensaje> mensajesList) {
        this.mensajesList = mensajesList;
        notifyDataSetChanged();
    }

    static class MensajeViewHolder extends RecyclerView.ViewHolder {
        TextView tvContenido, tvFecha;

        public MensajeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContenido = itemView.findViewById(R.id.tvContenido);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
