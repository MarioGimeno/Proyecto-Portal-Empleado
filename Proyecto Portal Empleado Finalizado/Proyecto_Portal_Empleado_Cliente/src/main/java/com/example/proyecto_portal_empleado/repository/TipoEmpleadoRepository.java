package com.example.proyecto_portal_empleado.repository;

import com.example.proyecto_portal_empleado.utils.TipoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEmpleadoRepository extends JpaRepository<TipoEmpleado, Integer> {
}
