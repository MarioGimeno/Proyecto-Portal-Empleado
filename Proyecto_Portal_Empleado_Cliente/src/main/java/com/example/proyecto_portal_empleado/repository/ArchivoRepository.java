package com.example.proyecto_portal_empleado.repository;

import com.example.proyecto_portal_empleado.utils.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.proyecto_portal_empleado.utils.Archivo;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    @Query("SELECT a.nombreArchivo, a.tipoArchivo, a.fechaSubida FROM Archivo a")
    List<Object[]> getArchivosInfo();
    // Cambiar el método para buscar por el objeto Usuario
    List<Archivo> findByCategoriaAndUsuario(String categoria, Optional<Usuario> usuario);
}
