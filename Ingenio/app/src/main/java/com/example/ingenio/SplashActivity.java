package com.example.ingenio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // configuramos la ventana para estar en pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // llamamos a un manejador para correr una tarea despues de un tiempo determinado
        new Handler().postDelayed(() -> {
            // creamos un intent para mostrar la pagina de login
            Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);

            startActivity(mainActivity);
            // terminamos la actividad actual
            finish();

        }, 2_000);
    }
}
