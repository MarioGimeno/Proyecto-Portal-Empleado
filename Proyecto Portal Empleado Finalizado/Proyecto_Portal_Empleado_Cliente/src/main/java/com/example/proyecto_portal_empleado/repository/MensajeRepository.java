package com.example.proyecto_portal_empleado.repository;

import com.example.proyecto_portal_empleado.utils.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByIdUsuario(int idUsuario);

}
