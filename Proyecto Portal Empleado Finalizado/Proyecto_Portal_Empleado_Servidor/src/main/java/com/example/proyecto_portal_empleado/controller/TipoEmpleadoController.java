package com.example.proyecto_portal_empleado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_portal_empleado.model.TipoEmpleado;
import com.example.proyecto_portal_empleado.service.TipoEmpleadoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-empleado")
public class TipoEmpleadoController {

    @Autowired
    private TipoEmpleadoService tipoEmpleadoService;

    // Obtener todos los tipos de empleado
    @GetMapping
    public List<TipoEmpleado> getAllTiposEmpleado() {
        return tipoEmpleadoService.getAllTiposEmpleado();
    }

    // Obtener un tipo de empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoEmpleado> getTipoEmpleadoById(@PathVariable int id) {
        Optional<TipoEmpleado> tipoEmpleado = tipoEmpleadoService.getTipoEmpleadoById(id);
        return tipoEmpleado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo tipo de empleado
    @PostMapping
    public TipoEmpleado createTipoEmpleado(@RequestBody TipoEmpleado tipoEmpleado) {
        return tipoEmpleadoService.createTipoEmpleado(tipoEmpleado);
    }

    // Actualizar un tipo de empleado existente
    @PutMapping("/{id}")
    public ResponseEntity<TipoEmpleado> updateTipoEmpleado(@PathVariable int id, @RequestBody TipoEmpleado tipoEmpleadoDetails) {
        TipoEmpleado updatedTipoEmpleado = tipoEmpleadoService.updateTipoEmpleado(id, tipoEmpleadoDetails);
        if (updatedTipoEmpleado != null) {
            return ResponseEntity.ok(updatedTipoEmpleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un tipo de empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoEmpleado(@PathVariable int id) {
        if (tipoEmpleadoService.deleteTipoEmpleado(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
