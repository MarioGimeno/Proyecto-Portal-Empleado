package com.example.proyecto_portal_empleado.contracts;


import com.example.proyecto_portal_empleado.entities.Mensaje;

import java.util.List;

public interface ContractMisMensajes {

    interface View {
        void showMensajes(List<Mensaje> mensajes);
        void showLoading();
        void hideLoading();
        void showError(String message);
    }

    interface Presenter {
        void cargarMensajes(int idUsuario);
    }

    interface Model {
        interface OnFinishedListener {
            void onFinished(List<Mensaje> mensajes);
            void onFailure(String errorMessage);
        }

        void obtenerMensajes(int idUsuario, OnFinishedListener listener);
    }
}
