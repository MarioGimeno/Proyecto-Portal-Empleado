package com.example.proyecto_portal_empleado.cpu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundTask {

    private ExecutorService executorService = Executors.newFixedThreadPool(2); // Pool de 2 hilos

    public void ejecutarTareaCostosa() {
        executorService.execute(() -> {
            // Código que consume muchos recursos (Ej. procesamiento de imágenes)
            procesarImagenes();
        });
    }

    private void procesarImagenes() {
        // Procesamiento de imágenes o tarea costosa
    }
}
