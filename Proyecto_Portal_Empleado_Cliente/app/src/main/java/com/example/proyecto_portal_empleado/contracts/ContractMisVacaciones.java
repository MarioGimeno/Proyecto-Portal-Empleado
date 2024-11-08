package com.example.proyecto_portal_empleado.contracts;

import com.example.proyecto_portal_empleado.entities.Vacacion;

import java.util.List;

public interface ContractMisVacaciones {
    interface View {
        void showVacaciones(List<Vacacion> vacaciones);
        void showLoading();
        void hideLoading();
        void showError(String message);
        void actualizarDiasRestantes(int diasRestantes);
        void showVacacionAgregada();  // Para notificar que la vacación fue agregada correctamente
    }

    interface Presenter {
        void cargarVacaciones(boolean isEditable, int usuarioId);
        void cargarVacacionesPorNombre(boolean isEditable, String nombre); // Método para cargar por nombre
        void agregarVacacion(String fechaInicio, String fechaFin, int usuarioId);
        void agregarVacacionPorNombre(String fechaInicio, String fechaFin, String nombreEmpleado); // Nuevo método para agregar por nombre
    }

    interface Model {
        interface OnFinishedListener {
            void onVacacionesCargadas(List<Vacacion> vacaciones);
            void onVacacionAgregada();
            void onFailure(String errorMessage);
        }
        interface OnUsuarioIdEncontradoListener {
            void onUsuarioIdEncontrado(int usuarioId);
            void onFailure(String errorMessage);
        }

        void cargarVacaciones(boolean isEditable, int usuarioId, OnFinishedListener listener);
        void cargarVacacionesPorNombre(boolean isEditable, String nombre, OnFinishedListener listener); // Agrega este método
        void agregarVacacion(String fechaInicio, String fechaFin, int diasRequeridos, int usuarioId, OnFinishedListener listener);
        void obtenerUsuarioIdPorNombre(String nombre, OnUsuarioIdEncontradoListener listener); // Método para obtener ID por nombre
    }
}
