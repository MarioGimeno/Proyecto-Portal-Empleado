package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.utils.Mensaje;
import com.example.proyecto_portal_empleado.utils.MensajeDTO;
import com.example.proyecto_portal_empleado.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<Void> enviarMensaje(@RequestBody MensajeDTO mensajeDTO) {
        // Convertir el campo 'fecha' (que viene como String) a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


        // Crear el mensaje utilizando los datos del DTO
        Mensaje mensaje = new Mensaje(mensajeDTO.getIdUsuario(), mensajeDTO.getContenido(), mensajeDTO.getFecha());
        System.out.println(mensaje.toString());
        // Guardar el mensaje en la base de datos
        mensajeService.guardarMensaje(mensaje);

        return ResponseEntity.ok().build();
    }
}
