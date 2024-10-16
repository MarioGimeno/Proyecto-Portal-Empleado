package com.example.proyecto_portal_empleado.repository;


import com.example.proyecto_portal_empleado.model.Fichaje;
import com.example.proyecto_portal_empleado.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FichajeRepository extends JpaRepository<Fichaje, Long> {

    // Cambiar la consulta para usar el objeto Usuario en lugar de usuarioId
    List<Fichaje> findByUsuario(Usuario usuario);
}
