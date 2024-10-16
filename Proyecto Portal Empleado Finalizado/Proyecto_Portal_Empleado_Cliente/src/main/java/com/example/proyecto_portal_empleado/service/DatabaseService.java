package com.example.proyecto_portal_empleado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    // Función para comprobar la conexión y obtener el nombre de la base de datos
    public String comprobarConexion() {
        try {
            // Ejecutamos una consulta simple para obtener el nombre de la base de datos
            String sql = "SELECT current_database()";
            String nombreBaseDeDatos = jdbcTemplate.queryForObject(sql, String.class);

            // Devolvemos un mensaje indicando el nombre de la base de datos conectada
            return "Conectado a la base de datos: " + nombreBaseDeDatos;
        } catch (Exception e) {
            // Si ocurre un error, devolvemos un mensaje indicando que no se pudo conectar
            return "Error al conectar a la base de datos: " + e.getMessage();
        }
    }
}
