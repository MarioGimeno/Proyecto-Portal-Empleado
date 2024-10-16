package com.example.proyecto_portal_empleado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.proyecto_portal_empleado.utils.TipoEmpleado;
import com.example.proyecto_portal_empleado.repository.TipoEmpleadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEmpleadoService {

    @Autowired
    @Qualifier("tipoEmpleadoRepository")
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    // Obtener todos los tipos de empleado
    public List<TipoEmpleado> getAllTiposEmpleado() {
        return tipoEmpleadoRepository.findAll();
    }

    // Obtener un tipo de empleado por ID
    public Optional<TipoEmpleado> getTipoEmpleadoById(int id) {
        return tipoEmpleadoRepository.findById(id);
    }

    // Crear un nuevo tipo de empleado
    public TipoEmpleado createTipoEmpleado(TipoEmpleado tipoEmpleado) {
        return tipoEmpleadoRepository.save(tipoEmpleado);
    }

    // Actualizar un tipo de empleado existente
    public TipoEmpleado updateTipoEmpleado(int id, TipoEmpleado tipoEmpleadoDetails) {
        Optional<TipoEmpleado> tipoEmpleadoOptional = tipoEmpleadoRepository.findById(id);
        if (tipoEmpleadoOptional.isPresent()) {
            TipoEmpleado tipoEmpleado = tipoEmpleadoOptional.get();
            // Cambia setTipoEmpleado por setNombre, ya que el campo es 'nombre'
            tipoEmpleado.setNombre(tipoEmpleadoDetails.getNombre());
            return tipoEmpleadoRepository.save(tipoEmpleado);
        }
        return null;
    }


    // Eliminar un tipo de empleado
    public boolean deleteTipoEmpleado(int id) {
        if (tipoEmpleadoRepository.existsById(id)) {
            tipoEmpleadoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
