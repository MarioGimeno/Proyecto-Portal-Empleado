package com.example.proyecto_portal_empleado.repository;


import com.example.proyecto_portal_empleado.model.Archivo;
import com.example.proyecto_portal_empleado.model.Fichaje;
import com.example.proyecto_portal_empleado.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FichajeRepository extends JpaRepository<Fichaje, Long> {

    // Cambiar la consulta para usar el objeto Usuario en lugar de usuarioId
    List<Fichaje> findByUsuario(Usuario usuario);
    @Query("SELECT a FROM Fichaje a JOIN a.usuario u WHERE u.nombre = :nombre")
    List<Fichaje> findFichajeByUsuarioNombre(@Param("nombre") String nombre);
}
