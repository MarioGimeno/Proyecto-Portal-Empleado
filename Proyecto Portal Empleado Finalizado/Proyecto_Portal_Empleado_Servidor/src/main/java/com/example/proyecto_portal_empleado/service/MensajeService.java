package com.example.proyecto_portal_empleado.service;


import com.example.proyecto_portal_empleado.model.Mensaje;
import com.example.proyecto_portal_empleado.model.Usuario;
import com.example.proyecto_portal_empleado.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class MensajeService {

    @Autowired
    @Qualifier("mensajeRepository")
    private MensajeRepository mensajeRepository;
    public List<Mensaje> obtenerMensajesPorUsuario(int idUsuario) {
        // Lógica para obtener los mensajes del usuario desde el repositorio
        return mensajeRepository.findByIdUsuario(idUsuario);
    }

    public void guardarMensaje(Mensaje mensaje) {
        // Establecer la fecha actual solo como día/mes/año
        mensaje.setFecha(LocalDate.now());
        // Guardar el mensaje en la base de datos
        mensajeRepository.save(mensaje);
    }

}
