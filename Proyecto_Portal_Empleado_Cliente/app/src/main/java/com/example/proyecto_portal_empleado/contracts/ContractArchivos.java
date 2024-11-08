package com.example.proyecto_portal_empleado.contracts;

import com.example.proyecto_portal_empleado.entities.Archivo;

import java.util.List;

public interface ContractArchivos {

    interface View {
        void mostrarArchivos(List<Archivo> archivos);
        void mostrarError(String mensaje);
        void irAVisualizarArchivo(String urlArchivo);
    }

    interface Presenter {
        void cargarArchivos(String categoria, int usuarioId);
        void cargarArchivosPorNombre(String categoria, String nombreUsuario);
        void onArchivoCargado(List<Archivo> archivos);
        void onError(String mensaje);
    }

    interface Model {
        void obtenerArchivos(String categoria, int usuarioId);
        void obtenerArchivosPorNombre(String categoria, String nombreUsuario);

    }
}
