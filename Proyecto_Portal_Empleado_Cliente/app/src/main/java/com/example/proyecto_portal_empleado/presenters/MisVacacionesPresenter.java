package com.example.proyecto_portal_empleado.presenters;

import com.example.proyecto_portal_empleado.contracts.ContractMisVacaciones;
import com.example.proyecto_portal_empleado.entities.Vacacion;
import com.example.proyecto_portal_empleado.model.MisVacacionesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MisVacacionesPresenter implements ContractMisVacaciones.Presenter {
    private ContractMisVacaciones.View view;
    private ContractMisVacaciones.Model model;

    public MisVacacionesPresenter(ContractMisVacaciones.View view) {
        this.view = view;
        this.model = new MisVacacionesModel();  // Instanciar el modelo
    }
    public void cargarVacacionesPorNombre(boolean isEditable, String nombre) {
        model.cargarVacacionesPorNombre(isEditable, nombre, new ContractMisVacaciones.Model.OnFinishedListener() {
            @Override
            public void onVacacionesCargadas(List<Vacacion> vacaciones) {
                view.hideLoading();  // Ocultar el indicador de carga
                view.showVacaciones(vacaciones);  // Mostrar las vacacio
            }

            @Override
            public void onVacacionAgregada() {

            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);  // Mostrar el error
            }
        });
    }
    @Override
    public void cargarVacaciones(boolean isEditable, int usuarioId) {
        view.showLoading();  // Mostrar el indicador de carga
        model.cargarVacaciones(isEditable, usuarioId, new ContractMisVacaciones.Model.OnFinishedListener() {
            @Override
            public void onVacacionesCargadas(List<Vacacion> vacaciones) {
                view.hideLoading();  // Ocultar el indicador de carga
                view.showVacaciones(vacaciones);  // Mostrar las vacaciones cargadas
            }

            @Override
            public void onVacacionAgregada() {
                // No se usa aquí
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);  // Mostrar el error
            }
        });
    }

    @Override
    public void agregarVacacion(String fechaInicio, String fechaFin, int usuarioId) {
        int diasRequeridos = calcularDiasVacacion(fechaInicio, fechaFin);

        if (diasRequeridos <= 0) {
            view.showError("Las fechas no son válidas");
            return;
        }

        model.agregarVacacion(fechaInicio, fechaFin, diasRequeridos, usuarioId, new ContractMisVacaciones.Model.OnFinishedListener() {
            @Override
            public void onVacacionAgregada() {
                // Notificar a la vista que la vacación fue agregada correctamente
                view.showVacacionAgregada();
                // Recargar la lista de vacaciones
                cargarVacaciones(true, usuarioId); // Suponemos que la lista debe ser editable luego de agregar una vacación
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);  // Mostrar el error si ocurre algún fallo
            }

            @Override
            public void onVacacionesCargadas(List<Vacacion> vacaciones) {
                // No se usa aquí
            }
        });
    }

    public void agregarVacacionPorNombre(String fechaInicio, String fechaFin, String nombreEmpleado) {
        model.obtenerUsuarioIdPorNombre(nombreEmpleado, new ContractMisVacaciones.Model.OnUsuarioIdEncontradoListener() {
            @Override
            public void onUsuarioIdEncontrado(int usuarioId) {
                agregarVacacion(fechaInicio, fechaFin, usuarioId);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);  // Mostrar el error si ocurre algún fallo
            }
        });
    }


    // Método para calcular los días entre las fechas
    private int calcularDiasVacacion(String fechaInicioStr, String fechaFinStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date fechaInicio = sdf.parse(fechaInicioStr);
            Date fechaFin = sdf.parse(fechaFinStr);

            if (fechaInicio != null && fechaFin != null) {
                // Calculamos la diferencia en milisegundos
                long diferenciaEnMilisegundos = fechaFin.getTime() - fechaInicio.getTime();
                // Convertimos la diferencia a días
                return (int) (diferenciaEnMilisegundos / (1000 * 60 * 60 * 24)) + 1;  // Incluyendo el día final
            } else {
                return 0;  // Si alguna de las fechas es null
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;  // Retornamos 0 en caso de error de formato
        }
    }

}
