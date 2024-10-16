package com.example.proyecto_portal_empleado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.proyecto_portal_empleado.utils.Vacacion;
import com.example.proyecto_portal_empleado.repository.VacacionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VacacionService {

    @Autowired
    @Qualifier("vacacionRepository")
    private VacacionRepository vacacionRepository;

    // Obtener todas las vacaciones
    public List<Vacacion> getAllVacaciones() {
        return vacacionRepository.findAll();
    }

    // Obtener vacaciones por ID
    public Optional<Vacacion> getVacacionById(int id) {
        return vacacionRepository.findById(id);
    }

    // Crear una nueva vacación
    public Vacacion createVacacion(Vacacion vacacion) {
        return vacacionRepository.save(vacacion);
    }
    public List<Vacacion> getVacacionesByUsuarioId(int idUsuario) {
        return vacacionRepository.findByUsuario_IdUsuario(idUsuario);  // Verifica que este método esté bien implementado
    }


    // Actualizar una vacación existente
    public Vacacion updateVacacion(int id, Vacacion vacacionDetails) {
        Optional<Vacacion> vacacionOptional = vacacionRepository.findById(id);
        if (vacacionOptional.isPresent()) {
            Vacacion vacacion = vacacionOptional.get();
            vacacion.setFechaInicio(vacacionDetails.getFechaInicio());
            vacacion.setFechaFin(vacacionDetails.getFechaFin());
            return vacacionRepository.save(vacacion);
        }
        return null;
    }

    // Eliminar una vacación
    public boolean deleteVacacion(int id) {
        if (vacacionRepository.existsById(id)) {
            vacacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
