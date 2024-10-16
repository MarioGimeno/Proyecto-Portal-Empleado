package com.example.proyecto_portal_empleado.repository;

import com.example.proyecto_portal_empleado.utils.Vacacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacacionRepository extends JpaRepository<Vacacion, Integer> {
    List<Vacacion> findByUsuario_IdUsuario(int idUsuario);  // Cambia "UsuarioId" a "Usuario_IdUsuario"
}

