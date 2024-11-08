package com.example.proyecto_portal_empleado.presenters;

import com.example.proyecto_portal_empleado.contracts.ContractArchivos;
import com.example.proyecto_portal_empleado.entities.Archivo;
import com.example.proyecto_portal_empleado.model.MisArchivosModel;

import java.util.List;

public class MisArchivosPresenter implements ContractArchivos.Presenter {

    private ContractArchivos.View view;
    private ContractArchivos.Model model;

    public MisArchivosPresenter(ContractArchivos.View view) {
        this.view = view;
        this.model = new MisArchivosModel(this); // Se conecta al modelo
    }

    @Override
    public void cargarArchivos(String categoria, int usuarioId) {
        model.obtenerArchivos(categoria, usuarioId); // Delegar al modelo
    }

    @Override
    public void cargarArchivosPorNombre(String categoria, String nombreUsuario) {
        model.obtenerArchivosPorNombre(categoria, nombreUsuario);
    }

    @Override
    public void onArchivoCargado(List<Archivo> archivos) {
        if (archivos != null && !archivos.isEmpty()) {
            view.mostrarArchivos(archivos);
        } else {
            view.mostrarError("No se encontraron archivos.");
        }
    }

    @Override
    public void onError(String mensaje) {
        view.mostrarError(mensaje);
    }
}
