package com.example.proyecto_portal_empleado.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_portal_empleado.R;
import com.example.proyecto_portal_empleado.entities.Archivo;

import java.util.List;

public class ArchivoAdapter extends RecyclerView.Adapter<ArchivoAdapter.ArchivoViewHolder> {

    private List<Archivo> archivos;
    private final OnArchivoClickListener archivoClickListener;

    public interface OnArchivoClickListener {
        void onArchivoClick(Archivo archivo);
    }

    public ArchivoAdapter(List<Archivo> archivos, OnArchivoClickListener listener) {
        this.archivos = archivos;
        this.archivoClickListener = listener;
    }

    @NonNull
    @Override
    public ArchivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_archivo, parent, false);
        return new ArchivoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchivoViewHolder holder, int position) {
        Archivo archivo = archivos.get(position);
        holder.bind(archivo, archivoClickListener);
    }

    @Override
    public int getItemCount() {
        return archivos.size();
    }

    public static class ArchivoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvArchivoNombre;

        public ArchivoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArchivoNombre = itemView.findViewById(R.id.tvNombreArchivo);
        }

        public void bind(final Archivo archivo, final OnArchivoClickListener listener) {
            tvArchivoNombre.setText(archivo.getNombreArchivo());

            itemView.setOnClickListener(v -> listener.onArchivoClick(archivo));
        }
    }
}
