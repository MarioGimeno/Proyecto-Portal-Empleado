    package com.example.proyecto_portal_empleado.utils;

    import com.example.proyecto_portal_empleado.entities.Mensaje;

    import java.util.List;

    import retrofit2.Call;
    import retrofit2.http.Body;
    import retrofit2.http.GET;
    import retrofit2.http.POST;
    import retrofit2.http.Path;

    public interface MensajeService {

        @GET("/api/mensajes/usuario/{idUsuario}")
        Call<List<Mensaje>> obtenerMensajes(@Path("idUsuario") int idUsuario);

        // Método para enviar un mensaje
        @POST("/api/mensajes/enviarMensaje")
        Call<Void> enviarMensaje(@Body Mensaje mensaje);
    }

