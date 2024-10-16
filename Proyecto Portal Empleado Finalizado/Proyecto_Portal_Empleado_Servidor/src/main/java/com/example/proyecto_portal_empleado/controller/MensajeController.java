package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.model.Mensaje;
import com.example.proyecto_portal_empleado.model.MensajeDTO;
import com.example.proyecto_portal_empleado.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;


    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Mensaje>> obtenerMensajes(@PathVariable int idUsuario) {
        // Obtener los mensajes del usuario utilizando el servicio
        List<Mensaje> mensajes = mensajeService.obtenerMensajesPorUsuario(idUsuario);

        // Si la lista está vacía, puedes devolver un NO_CONTENT, de lo contrario OK con los mensajes
        if (mensajes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(mensajes);
        }
    }


    @PostMapping("/enviarMensaje")
    public ResponseEntity<String> enviarMensaje(@RequestBody MensajeDTO mensajeDTO) {
        // Verificar si el campo 'fecha' está presente
        if (mensajeDTO.getFecha() == null || mensajeDTO.getFecha().isEmpty()) {
            return ResponseEntity.badRequest().build();  // Manejar error si la fecha es nula o vacía
        }

        // Intentar parsear la fecha con el formato dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(mensajeDTO.getFecha(), formatter); // Parsear a LocalDate
        } catch (DateTimeParseException e) {
            // Manejar error de formato de fecha
            return ResponseEntity.badRequest().body("Formato de fecha inválido");
        }

        // Crear el mensaje utilizando los datos del DTO
        // Utiliza LocalDate directamente
        Mensaje mensaje = new Mensaje(mensajeDTO.getIdUsuario(), mensajeDTO.getContenido(), fecha);
        System.out.println(mensaje.toString());

        // Guardar el mensaje en la base de datos
        mensajeService.guardarMensaje(mensaje);

        return ResponseEntity.ok().build();
    }

}
