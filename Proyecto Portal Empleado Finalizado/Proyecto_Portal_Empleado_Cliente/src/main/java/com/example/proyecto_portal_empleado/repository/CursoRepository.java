package com.example.proyecto_portal_empleado.repository;

import com.example.proyecto_portal_empleado.utils.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
