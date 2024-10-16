package com.example.proyecto_portal_empleado.repository;

import com.example.proyecto_portal_empleado.utils.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FichajeRepository extends JpaRepository<Fichaje, Long> {
    // Cambiar de usuario.id a usuario.idUsuario
    List<Fichaje> findByUsuarioIdUsuario(int idUsuario);
}
