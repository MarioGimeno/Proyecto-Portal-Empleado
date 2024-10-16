package com.example.proyecto_portal_empleado.service;

import com.example.proyecto_portal_empleado.utils.Fichaje;
import com.example.proyecto_portal_empleado.repository.CursoRepository;
import com.example.proyecto_portal_empleado.repository.FichajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FichajeService {

        @Autowired
        @Qualifier("fichajeRepository")
        private FichajeRepository fichajeRepository;
        public List<Fichaje> findAll(){
            return fichajeRepository.findAll();
        }

        public List<Fichaje> findByUsuarioId(int usuario_id){
            return fichajeRepository.findByUsuarioIdUsuario(usuario_id);
        }
        // Método para agregar un nuevo fichaje
        public Fichaje save(Fichaje fichaje) {
            return fichajeRepository.save(fichaje);  // Llama al repositorio para guardar el fichaje
        }
}
