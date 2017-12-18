package com.josevicente.gdriveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.josevicente.gdriveapp.BuscarFicheros.BuscarActivity;
import com.josevicente.gdriveapp.CrearFicheros.FicheroActivity;
import com.josevicente.gdriveapp.ListarFicheros.ListarActivity;


public class MenuActivity extends AppCompatActivity {
    //Declaramos las Variables
    private Button btnCrear,btnListar,btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Inicializamos las Variables
        btnCrear = findViewById(R.id.btnCrear);
        btnListar = findViewById(R.id.btnListar);
        btnBuscar = findViewById(R.id.btnBuscar);

        //Listener para el bntCrear
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFichero = new Intent(MenuActivity.this, FicheroActivity.class);
                startActivity(intentFichero);
            }
        });

        //Listener para el btnBsucar
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBuscar = new Intent(MenuActivity.this, BuscarActivity.class);
                startActivity(intentBuscar);

            }
        });

        //Listener Botón Listar
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentListar = new Intent(MenuActivity.this, ListarActivity.class);
                startActivity(intentListar);
            }
        });
    }

}
