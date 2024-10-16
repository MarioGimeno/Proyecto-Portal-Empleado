package com.example.proyecto_portal_empleado.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto_portal_empleado.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Método para ir a la actividad Mis Datos
    public void goToMisDatos(View view) {
        Intent intent = new Intent(MainActivity.this, MisDatosActivity.class);
        startActivity(intent);
    }

    // Método para ir a la actividad Mis Mensajes
    public void goToMisMensajes(View view) {
        Intent intent = new Intent(MainActivity.this, MisMensajesActivity.class);
        startActivity(intent);
    }

    // Método para ir a la actividad Mis Vacaciones
   public void goToMisVacaciones(View view) {
        Intent intent = new Intent(MainActivity.this, MisVacacionesActivity.class);
        startActivity(intent);
    }

    // Método para ir a la actividad Mis Vacaciones
    public void goToMisArchivos(View view) {
        Intent intent = new Intent(MainActivity.this, MisArchivosActivity.class);
        startActivity(intent);
    }
    public void goToLogin(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    // Método para ir a la actividad Mis Vacaciones
    public void goToMisFichajes(View view) {
        if (checkIfUserIsAdmin()){
            Intent intent = new Intent(MainActivity.this, HistorialFichajeActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this, FichajeActivity.class);
            startActivity(intent);
        }
    }
    public void goToMisHistorialFichajes(View view) {
        Intent intent = new Intent(MainActivity.this, HistorialFichajeActivity.class);
        startActivity(intent);
    }
    private boolean checkIfUserIsAdmin() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String userType = sharedPreferences.getString("tipoEmpleado", "empleado");
        return "Admin".equalsIgnoreCase(userType);
    }

    // Añadir más métodos para otras secciones si es necesario...
}
