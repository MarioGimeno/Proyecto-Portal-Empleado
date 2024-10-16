package com.example.proyecto_portal_empleado.contracts;

import com.example.proyecto_portal_empleado.entities.Fichaje;

import java.util.List;

public interface ContractHistorialFichajes {

    interface View {
        void showHistorial(List<Fichaje> fichajes);
        void showLoading();
        void hideLoading();
        void showError(String message);
    }

    interface Presenter {
        void cargarHistorialFichajes(int usuarioId);
    }

    interface Model {
        void obtenerHistorialFichajes(int usuarioId, OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onHistorialFichajesCargado(List<Fichaje> fichajes);
            void onFailure(String errorMessage);
        }
    }
}
