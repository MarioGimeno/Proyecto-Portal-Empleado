package com.example.proyecto_portal_empleado.contracts;

import com.example.proyecto_portal_empleado.entities.Fichaje;

import java.util.List;

public interface ContractFichajes {

    interface View {
        void showFichajes(List<Fichaje> fichajes);
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showFichajeAgregado();
    }

    interface Presenter {
        void agregarFichaje(Fichaje fichaje);
        void cargarFichajes(int usuarioId);
    }

    interface Model {
        interface OnFinishedListener {
            void onFichajeAgregado(Fichaje fichaje);
            void onFichajesCargados(List<Fichaje> fichajes);
            void onFailure(String errorMessage);
        }

        void agregarFichaje(Fichaje fichaje, OnFinishedListener onFinishedListener);
        void cargarFichajes(int usuarioId, OnFinishedListener onFinishedListener);
    }
}
