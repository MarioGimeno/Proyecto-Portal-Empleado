package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.utils.ArchivoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_portal_empleado.utils.Archivo;
import com.example.proyecto_portal_empleado.service.ArchivoService;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;
    // Endpoint para obtener archivos por categoría y ID de usuario
    @GetMapping("/categoria/{categoria}/usuario/{usuarioId}")
    public ResponseEntity<List<Archivo>> obtenerArchivosPorCategoriaYUsuarioId(
            @PathVariable String categoria,
            @PathVariable int usuarioId) {

        // Obtener archivos filtrados por categoría y usuarioId
        List<Archivo> archivos = archivoService.obtenerArchivosPorCategoriaYUsuarioId(categoria, usuarioId);

        if (!archivos.isEmpty()) {
            return ResponseEntity.ok(archivos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<List<ArchivoDTO>> getArchivosInfo() {
        List<ArchivoDTO> archivosInfo = archivoService.getArchivosInfo();

        if (!archivosInfo.isEmpty()) {
            return new ResponseEntity<>(archivosInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }



    // Obtener un archivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Archivo> getArchivoById(@PathVariable int id) {
        Optional<Archivo> archivo = archivoService.getArchivoById((long) id);
        return archivo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo archivo
    @PostMapping("/crearArchivo")
    public Archivo createArchivo(@RequestBody Archivo archivo) {
        return archivoService.createArchivo(archivo);
    }

    // Actualizar un archivo existente

    // Eliminar un archivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArchivo(@PathVariable int id) {
        if (archivoService.deleteArchivo((long) id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
