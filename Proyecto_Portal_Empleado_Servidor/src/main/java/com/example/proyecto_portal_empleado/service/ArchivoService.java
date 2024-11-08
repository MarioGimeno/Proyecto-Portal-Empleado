package com.example.proyecto_portal_empleado.service;

import com.example.proyecto_portal_empleado.model.Archivo;
import com.example.proyecto_portal_empleado.model.ArchivoDTO;
import com.example.proyecto_portal_empleado.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp; // Importa java.sql.Timestamp
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ArchivoService {

    @Autowired
    @Qualifier("archivoRepository")
    private ArchivoRepository archivoRepository;

    public List<Archivo> getAllArchivos() {
        return archivoRepository.findAll();
    }

    public Optional<Archivo> getArchivoById(Long id) {
        return archivoRepository.findById(id);  // Usa Long directamente
    }
    public List<Archivo> obtenerArchivosPorNombreUsuario(String categoria, String nombre) {
        return archivoRepository.findArchivosByCategoriaAndUsuarioNombre(categoria, nombre);
    }
        public Archivo createArchivo(Archivo archivo) {
        return archivoRepository.save(archivo);
    }



    public Archivo updateArchivo(Long id, Archivo archivoDetails) {
        Optional<Archivo> archivoOptional = archivoRepository.findById(id);
        if (archivoOptional.isPresent()) {
            Archivo archivo = archivoOptional.get();
            archivo.setNombreArchivo(archivoDetails.getNombreArchivo());
            archivo.setTipoArchivo(archivoDetails.getTipoArchivo());
            archivo.setUrlArchivo(archivoDetails.getUrlArchivo());
            archivo.setUsuario(archivoDetails.getUsuario());
            return archivoRepository.save(archivo);
        }
        return null;
    }
    public List<Archivo> getArchivosByCategoriaYUsuarioId(String categoria, Long usuarioId) {
        return archivoRepository.findByTipoArchivoAndUsuarioIdUsuario(categoria, usuarioId);
    }

    public boolean deleteArchivo(Long id) {
        Optional<Archivo> archivoOptional = archivoRepository.findById(id);
        if (archivoOptional.isPresent()) {
            archivoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
