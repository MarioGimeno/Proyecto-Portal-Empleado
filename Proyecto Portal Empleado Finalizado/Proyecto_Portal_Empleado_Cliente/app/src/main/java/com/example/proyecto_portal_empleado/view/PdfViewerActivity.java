package com.example.proyecto_portal_empleado.view;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_portal_empleado.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PdfViewerActivity extends AppCompatActivity {

    private ImageView pdfImageView;
    private PdfRenderer pdfRenderer;
    private ParcelFileDescriptor fileDescriptor;
    private PdfRenderer.Page currentPage;
    private Button btnAnterior, btnSiguiente, btnVolver;
    private int currentPageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        // Vincular el ImageView y los botones con el layout
        pdfImageView = findViewById(R.id.pdfImageView);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnVolver = findViewById(R.id.btnVolver);

        // Listener para el botón de volver
        btnVolver.setOnClickListener(v -> finish());

        // Listener para el botón Anterior
        btnAnterior.setOnClickListener(v -> {
            if (currentPageIndex > 0) {
                currentPageIndex--;
                showPage(currentPageIndex);
            }
        });

        // Listener para el botón Siguiente
        btnSiguiente.setOnClickListener(v -> {
            if (currentPageIndex < pdfRenderer.getPageCount() - 1) {
                currentPageIndex++;
                showPage(currentPageIndex);
            }
        });

        // Recibir la URL del archivo PDF desde el Intent
        String pdfUrl = getIntent().getStringExtra("urlArchivo");

        // Verificar si la URL no es nula o vacía antes de intentar descargar
        if (pdfUrl != null && !pdfUrl.isEmpty()) {
            Log.d("PdfViewerActivity", "URL del PDF: " + pdfUrl);
            downloadAndRenderPDF(pdfUrl);
        } else {
            Toast.makeText(this, "URL del archivo no proporcionada o es inválida", Toast.LENGTH_SHORT).show();
            finish();  // Cerrar la actividad si no hay URL
        }
    }

    private void downloadAndRenderPDF(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(PdfViewerActivity.this, "Error descargando el PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e("PdfViewerActivity", "Error en la descarga del PDF", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    File pdfFile = new File(getCacheDir(), "archivo_temp.pdf");

                    try (FileOutputStream outputStream = new FileOutputStream(pdfFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        long totalBytesRead = 0; // Para contar el total de bytes descargados
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            totalBytesRead += bytesRead;
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        Log.d("PdfViewerActivity", "Total bytes descargados: " + totalBytesRead);
                    }

                    runOnUiThread(() -> {
                        try {
                            openPdfRenderer(pdfFile);
                            showPage(currentPageIndex); // Mostrar la primera página
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(PdfViewerActivity.this, "Error descargando el PDF, respuesta del servidor: " + response.code(), Toast.LENGTH_SHORT).show());
                    Log.e("PdfViewerActivity", "Error al descargar el PDF, código de respuesta: " + response.code());
                }
            }
        });
    }

    private void openPdfRenderer(File pdfFile) throws IOException {
        fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
        pdfRenderer = new PdfRenderer(fileDescriptor);
    }

    private void showPage(int index) {
        if (pdfRenderer.getPageCount() <= index || index < 0) {
            return;
        }

        // Cerrar la página anterior
        if (currentPage != null) {
            currentPage.close();
        }

        // Abrir la nueva página
        currentPage = pdfRenderer.openPage(index);

        // Obtener el ancho de la pantalla
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        // FACTOR DE ZOOM
        float zoomFactor = 2.5f; // Cambia este valor para ajustar el zoom

        // Calcular la altura proporcional según la relación de aspecto de la página, aplicando el zoom
        int zoomedWidth = (int) (screenWidth * zoomFactor);
        int zoomedHeight = (int) ((float) zoomedWidth / currentPage.getWidth() * currentPage.getHeight());

        // Crear un Bitmap más grande del tamaño adecuado
        Bitmap bitmap = Bitmap.createBitmap(zoomedWidth, zoomedHeight, Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // Mostrar el Bitmap en el ImageView
        pdfImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (currentPage != null) {
                currentPage.close();
            }
            if (pdfRenderer != null) {
                pdfRenderer.close();
            }
            if (fileDescriptor != null) {
                fileDescriptor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
