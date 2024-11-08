package com.example.proyecto_portal_empleado.service;

import com.example.proyecto_portal_empleado.model.Usuario;
import com.example.proyecto_portal_empleado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.proyecto_portal_empleado.model.Vacacion;
import com.example.proyecto_portal_empleado.repository.VacacionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VacacionService {

    @Autowired
    @Qualifier("vacacionRepository")
    private VacacionRepository vacacionRepository;

    @Autowired
    @Qualifier("usuarioRepository")
    private UsuarioRepository usuarioRepository;

    // Obtener todas las vacaciones
    public List<Vacacion> getAllVacaciones() {
        return vacacionRepository.findAll();
    }
    public List<Vacacion> getVacacionByNombre(String nombre){ return vacacionRepository.findVacacionByUsuarioNombre(nombre);}
    // Obtener vacaciones por ID
    public Optional<Vacacion> getVacacionById(int id) {
        return vacacionRepository.findById(id);
    }

    public Vacacion createVacacion(Vacacion vacacion) {
        // Buscar el usuario por idUsuario
        Usuario usuario = usuarioRepository.findById(vacacion.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Asignar el objeto Usuario a la vacación
        vacacion.setUsuario(usuario);

        // Guardar la vacación
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
    // Aprobar una vacación
    public boolean aprobarVacacion(int id) {
        Optional<Vacacion> vacacionOpt = vacacionRepository.findById(id);
        if (vacacionOpt.isPresent()) {
            Vacacion vacacion = vacacionOpt.get();
            vacacion.setAprobada(true);
            vacacion.setPendiente(false);
            vacacionRepository.save(vacacion);
            return true;
        }
        return false;
    }

    // Rechazar una vacación
    public boolean rechazarVacacion(int id) {
        Optional<Vacacion> vacacionOpt = vacacionRepository.findById(id);
        if (vacacionOpt.isPresent()) {
            Vacacion vacacion = vacacionOpt.get();
            vacacion.setAprobada(false);
            vacacion.setPendiente(false);
            vacacionRepository.save(vacacion);
            return true;
        }
        return false;
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
