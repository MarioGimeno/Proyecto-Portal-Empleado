package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.utils.Fichaje;
import com.example.proyecto_portal_empleado.repository.FichajeRepository;
import com.example.proyecto_portal_empleado.service.FichajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fichajes")
public class FichajeController {

    @Autowired
    private FichajeService fichajeService;

    @GetMapping("/")
    public List<Fichaje> findAll(){
        return fichajeService.findAll();
    }

    // Obtener todos los fichajes de un usuario por su ID
    @GetMapping("/usuario/{id}")
    public List<Fichaje> getFichajesPorUsuario(@PathVariable int id) {
        return fichajeService.findByUsuarioId(id);
    }

    // Endpoint para agregar un nuevo fichaje
    @PostMapping("/agregar")
    public ResponseEntity<Fichaje> agregarFichaje(@RequestBody Fichaje fichaje) {
        try {
            Fichaje nuevoFichaje = fichajeService.save(fichaje);
            return new ResponseEntity<>(nuevoFichaje, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
