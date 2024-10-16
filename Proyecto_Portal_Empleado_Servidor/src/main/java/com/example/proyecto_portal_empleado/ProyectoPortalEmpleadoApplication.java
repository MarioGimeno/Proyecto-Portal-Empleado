package com.example.proyecto_portal_empleado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.proyecto_portal_empleado.repository")  // Aquí incluimos el paquete donde están los repositorios
public class ProyectoPortalEmpleadoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoPortalEmpleadoApplication.class, args);
    }
}
