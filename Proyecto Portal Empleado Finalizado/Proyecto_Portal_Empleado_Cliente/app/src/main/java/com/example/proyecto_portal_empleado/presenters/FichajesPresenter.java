package com.example.proyecto_portal_empleado.presenters;

import com.example.proyecto_portal_empleado.contracts.ContractFichajes;
import com.example.proyecto_portal_empleado.entities.Fichaje;
import com.example.proyecto_portal_empleado.model.FichajesModel;

import java.util.List;

public class FichajesPresenter implements ContractFichajes.Presenter {

    private ContractFichajes.View view;
    private ContractFichajes.Model model;

    public FichajesPresenter(ContractFichajes.View view) {
        this.view = view;
        this.model = new FichajesModel();
    }

    @Override
    public void agregarFichaje(Fichaje fichaje) {
        model.agregarFichaje(fichaje, new ContractFichajes.Model.OnFinishedListener() {
            @Override
            public void onFichajeAgregado(Fichaje fichaje) {
                view.showFichajeAgregado();
            }

            @Override
            public void onFichajesCargados(List<Fichaje> fichajes) {
                // No se usa aquí
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void cargarFichajes(int usuarioId) {
        view.showLoading();
        model.cargarFichajes(usuarioId, new ContractFichajes.Model.OnFinishedListener() {
            @Override
            public void onFichajeAgregado(Fichaje fichaje) {
                // No se usa aquí
            }

            @Override
            public void onFichajesCargados(List<Fichaje> fichajes) {
                view.hideLoading();
                view.showFichajes(fichajes);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }
}
