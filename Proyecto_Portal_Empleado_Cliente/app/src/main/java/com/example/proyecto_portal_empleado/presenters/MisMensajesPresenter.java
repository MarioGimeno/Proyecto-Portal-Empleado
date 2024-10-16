package com.example.proyecto_portal_empleado.presenters;

import com.example.proyecto_portal_empleado.contracts.ContractMisMensajes;
import com.example.proyecto_portal_empleado.entities.Mensaje;
import com.example.proyecto_portal_empleado.model.MisMensajesModel;

import java.util.List;

public class MisMensajesPresenter implements ContractMisMensajes.Presenter {

    private ContractMisMensajes.View view;
    private ContractMisMensajes.Model model;

    public MisMensajesPresenter(ContractMisMensajes.View view) {
        this.view = view;
        this.model = new MisMensajesModel(); // El modelo obtiene los mensajes de la API
    }

    @Override
    public void cargarMensajes(int idUsuario) {
        view.showLoading();  // Mostrar indicador de carga mientras se obtienen los datos

        model.obtenerMensajes(idUsuario, new ContractMisMensajes.Model.OnFinishedListener() {
            @Override
            public void onFinished(List<Mensaje> mensajes) {
                view.hideLoading();  // Ocultar indicador de carga
                view.showMensajes(mensajes);  // Mostrar los mensajes en la vista
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();  // Ocultar indicador de carga en caso de error
                view.showError(errorMessage);  // Mostrar mensaje de error en la vista
            }
        });
    }
}
