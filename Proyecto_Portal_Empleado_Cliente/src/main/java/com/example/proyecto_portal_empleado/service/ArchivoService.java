package com.example.proyecto_portal_empleado.service;

import com.example.proyecto_portal_empleado.utils.Archivo;
import com.example.proyecto_portal_empleado.utils.ArchivoDTO;
import com.example.proyecto_portal_empleado.utils.Usuario;
import com.example.proyecto_portal_empleado.repository.ArchivoRepository;
import com.example.proyecto_portal_empleado.repository.UsuarioRepository;
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
    @Autowired
    private UsuarioService usuarioService;


    public List<Archivo> getAllArchivos() {
        return archivoRepository.findAll();
    }

    public Optional<Archivo> getArchivoById(Long id) {
        return archivoRepository.findById(id);  // Usa Long directamente
    }
    public List<Archivo> obtenerArchivosPorCategoriaYUsuarioId(String categoria, int usuarioId) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(usuarioId);
        return archivoRepository.findByCategoriaAndUsuario(categoria, usuario);
    }

    public Archivo createArchivo(Archivo archivo) {
        return archivoRepository.save(archivo);
    }
    public List<ArchivoDTO> getArchivosInfo() {
        List<Object[]> resultados = archivoRepository.getArchivosInfo();
        return resultados.stream()
                .map(result -> new ArchivoDTO(
                        (String) result[0],             // nombreArchivo
                        (String) result[1],             // tipoArchivo
                        ((LocalDateTime) result[2]) // usar LocalDateTime directamente
                ))
                .collect(Collectors.toList());
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
