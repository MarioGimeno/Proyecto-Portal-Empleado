package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.model.Fichaje;
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

    // Obtener todos los fichajes por usuario
    @GetMapping("/usuario")
    public ResponseEntity<List<Fichaje>> obtenerFichajesPorUsuario(@RequestParam int idUsuario) {
        List<Fichaje> fichajes = fichajeService.obtenerFichajesPorUsuario(idUsuario);
        if (fichajes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fichajes, HttpStatus.OK);
    }
    // Obtener todos los fichajes por usuario
    @GetMapping("/usuario/nombre")
    public ResponseEntity<List<Fichaje>> obtenerFichajesPorUsuario(@RequestParam String idUsuario) {
        List<Fichaje> fichajes = fichajeService.obtenerFichajesPorNombreUsuario(idUsuario);
        if (fichajes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fichajes, HttpStatus.OK);
    }

    // Agregar un nuevo fichaje
    @PostMapping("/agregar")
    public ResponseEntity<Fichaje> agregarFichaje(@RequestBody Fichaje nuevoFichaje) {
        System.out.println(nuevoFichaje.toString());
        Fichaje fichajeGuardado = fichajeService.agregarFichaje(nuevoFichaje);
        return new ResponseEntity<>(fichajeGuardado, HttpStatus.CREATED);
    }
}
