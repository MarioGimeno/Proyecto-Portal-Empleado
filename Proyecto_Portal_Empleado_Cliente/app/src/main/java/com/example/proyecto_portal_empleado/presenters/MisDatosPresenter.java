package com.example.proyecto_portal_empleado.presenters;


import android.content.Context;

import com.example.proyecto_portal_empleado.contracts.ContractMisDatos;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.entities.Usuario;
import com.example.proyecto_portal_empleado.model.MisDatosModel;

public class MisDatosPresenter implements ContractMisDatos.Presenter {

    private ContractMisDatos.View view;
    private ContractMisDatos.Model model;

    // Constructor que recibe la vista y el contexto
    public MisDatosPresenter(ContractMisDatos.View view, Context context) {
        this.view = view;
        this.model = new MisDatosModel(context);  // Crear el modelo y pasar el contexto
    }
    @Override
    public void fetchUsuarioPorNombre(String nombre) {
        view.showLoading();  // Mostrar un indicador de carga

        // Usar el modelo para obtener los datos del usuario por nombre
        model.fetchUsuarioPorNombre(nombre, new ContractMisDatos.Model.OnFinishedListener() {
            @Override
            public void onUsuarioLoaded(Usuario usuario) {
                view.hideLoading();  // Ocultar el indicador de carga
                view.onUsuarioLoaded(usuario);  // Notificar a la vista que el usuario fue cargado
            }

            @Override
            public void onUsuarioUpdated() {
                // No se usa en esta operación
            }

            @Override
            public void onMensajeEnviado() {
                // No se usa en esta operación
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();  // Ocultar el indicador de carga en caso de error
                view.showError(errorMessage);  // Mostrar el mensaje de error en la vista
            }
        });
    }

    @Override
    public void fetchUsuario(int id) {
        view.showLoading();  // Mostrar un indicador de carga mientras se obtienen los datos

        // Usar el modelo para obtener los datos del usuario
        model.fetchUsuario(id, new ContractMisDatos.Model.OnFinishedListener() {
            @Override
            public void onUsuarioLoaded(Usuario usuario) {
                view.hideLoading();  // Ocultar el indicador de carga
                view.onUsuarioLoaded(usuario);  // Notificar a la vista que los datos fueron cargados
            }

            @Override
            public void onUsuarioUpdated() {
                // No se usa en esta operación
            }

            @Override
            public void onMensajeEnviado() {
                // No se usa en esta operación
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();  // Ocultar el indicador de carga en caso de error
                view.showError(errorMessage);  // Mostrar el mensaje de error en la vista
            }
        });
    }

    @Override
    public void actualizarUsuario(int id, Usuario usuario) {
        view.showLoading();  // Mostrar un indicador de carga mientras se actualizan los datos

        // Usar el modelo para actualizar los datos del usuario
        model.actualizarUsuario(id, usuario, new ContractMisDatos.Model.OnFinishedListener() {
            @Override
            public void onUsuarioLoaded(Usuario usuario) {
                // No se usa en esta operación
            }

            @Override
            public void onUsuarioUpdated() {
                view.hideLoading();  // Ocultar el indicador de carga
                view.onUsuarioUpdated();  // Notificar a la vista que los datos fueron actualizados
            }

            @Override
            public void onMensajeEnviado() {
                // No se usa en esta operación
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();  // Ocultar el indicador de carga en caso de error
                view.showError(errorMessage);  // Mostrar el mensaje de error en la vista
            }
        });
    }

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        view.showLoading();  // Mostrar un indicador de carga mientras se envía el mensaje

        // Usar el modelo para enviar el mensaje
        model.enviarMensaje(mensaje, new ContractMisDatos.Model.OnFinishedListener() {
            @Override
            public void onUsuarioLoaded(Usuario usuario) {
                // No se usa en esta operación
            }

            @Override
            public void onUsuarioUpdated() {
                // No se usa en esta operación
            }

            @Override
            public void onMensajeEnviado() {
                view.hideLoading();  // Ocultar el indicador de carga
                view.onMensajeEnviado();  // Notificar a la vista que el mensaje fue enviado
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();  // Ocultar el indicador de carga en caso de error
                view.showError(errorMessage);  // Mostrar el mensaje de error en la vista
            }
        });
    }
}
