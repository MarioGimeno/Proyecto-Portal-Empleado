package com.example.proyecto_portal_empleado.controller;

import com.example.proyecto_portal_empleado.model.ArchivoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_portal_empleado.model.Archivo;
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

    @GetMapping("/")
    public ResponseEntity<byte[]> getArchivo() {
        // Obtener el primer archivo de la base de datos (o modificar según tu necesidad)
        List<Archivo> archivos = archivoService.getAllArchivos();

        // Depurar la cantidad de archivos obtenidos
        System.out.println("Cantidad de archivos: " + archivos.size());

        if (!archivos.isEmpty()) {
            Archivo archivo = archivos.get(0); // Aquí estamos obteniendo el primer archivo
            String base64FileData = archivo.getUrlArchivo();
            byte[] fileData = Base64.getDecoder().decode(base64FileData); // Convertir Base64 a byte[]

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(archivo.getTipoArchivo())); // Tipo MIME desde la base de datos
            headers.setContentDispositionFormData("inline", archivo.getNombreArchivo());

            // Devolver el archivo en la respuesta HTTP
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si no hay archivos
        }
    }





    @GetMapping("/descargar/{id}")
    public ResponseEntity<byte[]> descargarArchivo(@PathVariable Long id) {
        Optional<Archivo> archivoOptional = archivoService.getArchivoById(id);

        if (archivoOptional.isPresent()) {
            Archivo archivo = archivoOptional.get();
            String base64FileData = archivo.getUrlArchivo();
            byte[] fileData = Base64.getDecoder().decode(base64FileData); // Convertir Base64 a byte[]

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", archivo.getNombreArchivo());

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    @PutMapping("/{id}")
    public ResponseEntity<Archivo> updateArchivo(@PathVariable int id, @RequestBody Archivo archivoDetails) {
        Archivo updatedArchivo = archivoService.updateArchivo((long) id, archivoDetails);
        if (updatedArchivo != null) {
            return ResponseEntity.ok(updatedArchivo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/categoria/{categoria}/usuario/{usuarioId}")
    public List<Archivo> getArchivosByCategoriaYUsuarioId(@PathVariable String categoria, @PathVariable Long usuarioId) {
        return archivoService.getArchivosByCategoriaYUsuarioId(categoria, usuarioId);
    }
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
