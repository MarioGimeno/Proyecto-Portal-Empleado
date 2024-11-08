package com.example.proyecto_portal_empleado.contracts;

import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.entities.Usuario;

public interface ContractMisDatos {

    interface View {
        void onUsuarioLoaded(Usuario usuario);
        void onUsuarioUpdated();
        void onMensajeEnviado();
        void onFailure(String errorMessage);

        void showLoading();
        void hideLoading();
        void showError(String errorMessage);
    }

    interface Presenter {
        void fetchUsuario(int id);
        void fetchUsuarioPorNombre(String nombre);  // Sin retorno
        void actualizarUsuario(int id, Usuario usuario);
        void enviarMensaje(Mensaje mensaje);
    }

    interface Model {
        interface OnFinishedListener {
            void onUsuarioLoaded(Usuario usuario);
            void onUsuarioUpdated();
            void onMensajeEnviado();
            void onFailure(String errorMessage);
        }

        void fetchUsuarioPorNombre(String nombre, OnFinishedListener listener);  // Cambio a void
        void fetchUsuario(int id, OnFinishedListener listener);
        void actualizarUsuario(int id, Usuario usuario, OnFinishedListener listener);
        void enviarMensaje(Mensaje mensaje, OnFinishedListener listener);
    }
}
