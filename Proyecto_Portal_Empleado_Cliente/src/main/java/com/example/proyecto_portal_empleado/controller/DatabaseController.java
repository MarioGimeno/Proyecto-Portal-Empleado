package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    // Endpoint para comprobar la conexión
    @GetMapping("/check-connection")
    public String checkConnection() {
        return databaseService.comprobarConexion();
    }
}
