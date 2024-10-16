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
        void agregarVacacion(String fechaInicio, String fechaFin, int usuarioId);
    }

    interface Model {
        interface OnFinishedListener {
            void onVacacionesCargadas(List<Vacacion> vacaciones);
            void onVacacionAgregada();
            void onFailure(String errorMessage);
        }

        void cargarVacaciones(boolean isEditable, int usuarioId, OnFinishedListener listener);
        void agregarVacacion(String fechaInicio, String fechaFin, int diasRequeridos, int usuarioId, OnFinishedListener listener);
    }
}
