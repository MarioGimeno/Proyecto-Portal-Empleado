package com.example.proyecto_portal_empleado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.proyecto_portal_empleado.utils.Curso;
import com.example.proyecto_portal_empleado.repository.CursoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    @Qualifier("cursoRepository")
    private CursoRepository cursoRepository;

    // Obtener todos los cursos
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    // Obtener un curso por ID
    public Optional<Curso> getCursoById(int id) {
        return cursoRepository.findById(id);
    }

    // Crear un nuevo curso
    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    // Actualizar un curso existente
    public Curso updateCurso(int id, Curso cursoDetails) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            curso.setCurso(cursoDetails.getCurso());
            curso.setLinkCertificacion(cursoDetails.getLinkCertificacion());
            return cursoRepository.save(curso);
        }
        return null;
    }

    // Eliminar un curso
    public boolean deleteCurso(int id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
