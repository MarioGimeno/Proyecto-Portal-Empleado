package com.example.proyecto_portal_empleado.repository;

import com.example.proyecto_portal_empleado.model.ArchivoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.proyecto_portal_empleado.model.Archivo;

import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    @Query("SELECT a.nombreArchivo, a.tipoArchivo, a.fechaSubida FROM Archivo a")
    List<Object[]> getArchivosInfo();
    List<Archivo> findByTipoArchivoAndUsuarioIdUsuario(String tipoArchivo, Long idUsuario);
    @Query("SELECT a FROM Archivo a JOIN a.usuario u WHERE u.nombre = :nombre AND a.tipoArchivo = :categoria")
    List<Archivo> findArchivosByCategoriaAndUsuarioNombre(@Param("categoria") String categoria, @Param("nombre") String nombre);

}
