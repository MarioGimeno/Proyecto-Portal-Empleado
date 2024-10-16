package com.example.proyecto_portal_empleado.presenters;

import com.example.proyecto_portal_empleado.contracts.ContractHistorialFichajes;
import com.example.proyecto_portal_empleado.entities.Fichaje;
import com.example.proyecto_portal_empleado.model.HistorialFichajesModel;

import java.util.List;

public class HistorialFichajesPresenter implements ContractHistorialFichajes.Presenter {

    private ContractHistorialFichajes.View view;
    private ContractHistorialFichajes.Model model;

    public HistorialFichajesPresenter(ContractHistorialFichajes.View view) {
        this.view = view;
        this.model = new HistorialFichajesModel();
    }

    @Override
    public void cargarHistorialFichajes(int usuarioId) {
        view.showLoading();
        model.obtenerHistorialFichajes(usuarioId, new ContractHistorialFichajes.Model.OnFinishedListener() {
            @Override
            public void onHistorialFichajesCargado(List<Fichaje> fichajes) {
                view.hideLoading();
                view.showHistorial(fichajes);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }
}
