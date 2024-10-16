package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.utils.Usuario;
import com.example.proyecto_portal_empleado.DTO.VacacionRequest;
import com.example.proyecto_portal_empleado.repository.UsuarioRepository;
import com.example.proyecto_portal_empleado.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_portal_empleado.utils.Vacacion;
import com.example.proyecto_portal_empleado.service.VacacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vacaciones")
public class VacacionController {

    @Autowired
    private VacacionService vacacionService;

    @Autowired  // Inyecta automáticamente el repositorio de usuarios
    private UsuarioService usuarioService;
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
    @PostMapping("/agregar")
    public  ResponseEntity<Void> createVacacion(@RequestBody VacacionRequest vacacionRequest) {

        // Buscar el usuario por su ID
        Usuario usuario = usuarioService.getUsuarioById(vacacionRequest.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear una nueva vacación
        Vacacion vacacion = new Vacacion();
        vacacion.setFechaInicio(vacacionRequest.getFechaInicio());
        vacacion.setFechaFin(vacacionRequest.getFechaFin());
        vacacion.setDias(vacacionRequest.getDias());
        vacacion.setAprobada(false);
        vacacion.setUsuario(usuario);  // Asociar el objeto Usuario a la vacación
        vacacion.setPendiente(true);

        // Guardar la vacación
        vacacionService.createVacacion(vacacion);

        return ResponseEntity.ok().build();
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
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Void> aprobarVacacion(@PathVariable int id) {
        Optional<Vacacion> vacacionOptional = vacacionService.getVacacionById(id);
        System.out.println(vacacionOptional.toString());
        if (vacacionOptional.isPresent()) {
            Vacacion vacacion = vacacionOptional.get();
            vacacion.setAprobada(true);
            vacacionService.updateVacacion(vacacion.getIdVacacion(), vacacion);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();  // Aquí se devuelve el 404 si no se encuentra
        }
    }
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Void> rechazarVacacion(@PathVariable int id) {
        Optional<Vacacion> vacacionOptional = vacacionService.getVacacionById(id);
        System.out.println(vacacionOptional.toString());
        if (vacacionOptional.isPresent()) {
            Vacacion vacacion = vacacionOptional.get();
            vacacion.setAprobada(false);
            vacacion.setPendiente(false);
            vacacionService.updateVacacion(vacacion.getIdVacacion(), vacacion);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();  // Aquí se devuelve el 404 si no se encuentra
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
