package com.example.proyecto_portal_empleado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_portal_empleado.model.Vacacion;
import com.example.proyecto_portal_empleado.service.VacacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vacaciones")
public class VacacionController {

    @Autowired
    private VacacionService vacacionService;

    // Obtener todas las vacaciones
    @GetMapping
    public List<Vacacion> getAllVacaciones() {
        return vacacionService.getAllVacaciones();
    }

    // Obtener una vacación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vacacion> getVacacionById(@PathVariable int id) {
        Optional<Vacacion> vacacion = vacacionService.getVacacionById(id);
        return vacacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Vacacion>> getVacacionesByUsuarioId(@PathVariable int id) {
        List<Vacacion> vacaciones = vacacionService.getVacacionesByUsuarioId(id);
        if (!vacaciones.isEmpty()) {
            return ResponseEntity.ok(vacaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Crear una nueva vacación
    @PostMapping
    public Vacacion createVacacion(@RequestBody Vacacion vacacion) {
        return vacacionService.createVacacion(vacacion);
    }
    // Agregar nueva vacación
    @PostMapping("/agregar")
    public ResponseEntity<Vacacion> agregarVacacion(@RequestBody Vacacion nuevaVacacion) {
        System.out.println(nuevaVacacion.toString());
        Vacacion vacacionGuardada = vacacionService.createVacacion(nuevaVacacion);
        return new ResponseEntity<>(vacacionGuardada, HttpStatus.CREATED);
    }
    // Actualizar una vacación existente
    @PutMapping("/{id}")
    public ResponseEntity<Vacacion> updateVacacion(@PathVariable int id, @RequestBody Vacacion vacacionDetails) {
        Vacacion updatedVacacion = vacacionService.updateVacacion(id, vacacionDetails);
        if (updatedVacacion != null) {
            return ResponseEntity.ok(updatedVacacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /*@PutMapping("/vacaciones/{id}/aprobar")
    public ResponseEntity<Void> aprobarVacacion(@PathVariable int id) {
        Optional<Vacacion> vacacionOptional = vacacionService.getVacacionById(id);
        int idVacacion = vacacionOptional.get().getIdVacacion();

        if (vacacionOptional.isPresent()) {
            Vacacion vacacion = vacacionOptional.get();
            vacacion.setAprobada(true);
            vacacionService.updateVacacion(idVacacion, vacacion);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    // Aprobar una vacación
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Void> aprobarVacacion(@PathVariable int id) {
        boolean aprobado = vacacionService.aprobarVacacion(id);
        if (aprobado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Rechazar una vacación
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Void> rechazarVacacion(@PathVariable int id) {
        boolean rechazado = vacacionService.rechazarVacacion(id);
        if (rechazado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una vacación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacacion(@PathVariable int id) {
        if (vacacionService.deleteVacacion(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
