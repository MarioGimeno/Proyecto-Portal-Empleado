package com.example.proyecto_portal_empleado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_portal_empleado.model.Usuario;
import com.example.proyecto_portal_empleado.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable int id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo usuario
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioDetails) {
        // Buscar el usuario existente en la base de datos
        Optional<Usuario> optionalUsuario = usuarioService.getUsuarioById(id);
        System.out.println(usuarioDetails.toString());
        // Verificar si el usuario existe
        if (optionalUsuario.isPresent()) {
            Usuario usuarioExistente = optionalUsuario.get();

            // Actualizar solo los campos proporcionados en 'usuarioDetails'
            if (usuarioDetails.getNombre() != null && !usuarioDetails.getNombre().isEmpty()) {
                usuarioExistente.setNombre(usuarioDetails.getNombre());
            }
            if (usuarioDetails.getApellidos() != null && !usuarioDetails.getApellidos().isEmpty()) {
                usuarioExistente.setApellidos(usuarioDetails.getApellidos());
            }
            if (usuarioDetails.getEmail() != null && !usuarioDetails.getEmail().isEmpty()) {
                usuarioExistente.setEmail(usuarioDetails.getEmail());
            }
            if (usuarioDetails.getCuentaBancaria() != null && !usuarioDetails.getCuentaBancaria().isEmpty()) {
                usuarioExistente.setCuentaBancaria(usuarioDetails.getCuentaBancaria());
            }
            if (usuarioDetails.getNumSeguridadSocial() != null && !usuarioDetails.getNumSeguridadSocial().isEmpty()) {
                usuarioExistente.setNumSeguridadSocial(usuarioDetails.getNumSeguridadSocial());
            }

            // Solo actualizar la contraseña si se ha proporcionado una nueva
            if (usuarioDetails.getPassword() != null && !usuarioDetails.getPassword().isEmpty()) {
                usuarioExistente.setPassword(usuarioDetails.getPassword());
            }
// Actualizar los nuevos campos
            if (usuarioDetails.getPuestoTrabajo() != null && !usuarioDetails.getPuestoTrabajo().isEmpty()) {
                usuarioExistente.setPuestoTrabajo(usuarioDetails.getPuestoTrabajo());
            }
            if (usuarioDetails.getDepartamento() != null && !usuarioDetails.getDepartamento().isEmpty()) {
                usuarioExistente.setDepartamento(usuarioDetails.getDepartamento());
            }
            if (usuarioDetails.getFechaContratacion() != null && !usuarioDetails.getFechaContratacion().isEmpty()) {
                usuarioExistente.setFechaContratacion(usuarioDetails.getFechaContratacion());
            }
            if (usuarioDetails.getTelefonoContacto() != null && !usuarioDetails.getTelefonoContacto().isEmpty()) {
                usuarioExistente.setTelefonoContacto(usuarioDetails.getTelefonoContacto());
            }
            if (usuarioDetails.getDireccion() != null && !usuarioDetails.getDireccion().isEmpty()) {
                usuarioExistente.setDireccion(usuarioDetails.getDireccion());
            }
            if (usuarioDetails.getFechaNacimiento() != null && !usuarioDetails.getFechaNacimiento().isEmpty()) {
                usuarioExistente.setFechaNacimiento(usuarioDetails.getFechaNacimiento());
            }

            // Guardar el usuario actualizado
            Usuario usuarioActualizado = usuarioService.updateUsuario(id, usuarioExistente);

            // Devolver el usuario actualizado
            return ResponseEntity.ok(usuarioActualizado);

        } else {
            // Si el usuario no es encontrado, devolver 404
            return ResponseEntity.notFound().build();
        }
    }


    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {
        if (usuarioService.deleteUsuario(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuario = usuarioService.login(loginRequest.getEmail(), loginRequest.getPassword());
        System.out.println(loginRequest.toString());
        if (usuario.isPresent()) {
            // Devolver todo el objeto Usuario
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(401).body("Correo o contraseña incorrectos");
        }
    }

}
