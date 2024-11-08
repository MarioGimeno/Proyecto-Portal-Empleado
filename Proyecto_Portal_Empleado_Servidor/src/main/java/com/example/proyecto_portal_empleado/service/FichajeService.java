package com.example.proyecto_portal_empleado.service;


import com.example.proyecto_portal_empleado.model.Fichaje;
import com.example.proyecto_portal_empleado.model.Usuario;
import com.example.proyecto_portal_empleado.repository.FichajeRepository;
import com.example.proyecto_portal_empleado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FichajeService {
    @Autowired
    private FichajeRepository fichajeRepository;
    @Autowired
    @Qualifier("usuarioRepository")
    private UsuarioRepository usuarioRepository;
    public FichajeService(FichajeRepository fichajeRepository) {
        this.fichajeRepository = fichajeRepository;
    }

    public List<Fichaje> obtenerFichajesPorUsuario(int usuarioId) {
        // Obtener la entidad Usuario por el id
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar fichajes por el Usuario
        return fichajeRepository.findByUsuario(usuario);
    }

    public List<Fichaje> obtenerFichajesPorNombreUsuario(String nombreUsuario) {
        return fichajeRepository.findFichajeByUsuarioNombre(nombreUsuario);
    }

    public Fichaje agregarFichaje(Fichaje fichaje) {
        System.out.println(fichaje.getUsuarioId());
        Usuario usuario = usuarioRepository.findById(fichaje.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Asignar el objeto Usuario a la vacación
        fichaje.setUsuario(usuario);

        // Guardar la vacación
        return fichajeRepository.save(fichaje);
    }
}
