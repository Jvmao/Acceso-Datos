package com.josevicente.firebaseproject.Main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.josevicente.firebaseproject.ListAllItems.ProductosActivity;
import com.josevicente.firebaseproject.LoginUser.LoginActivity;
import com.josevicente.firebaseproject.UserControl.UserActivity;
import com.josevicente.firebaseproject.R;

public class MainActivity extends AppCompatActivity {
    //Declaramos las Variables de los Botones
    private Button btnNuevoUsuario;
    private Button btnLogin;
    private Button btnProducto;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos las Variables
        btnNuevoUsuario = findViewById(R.id.btnCrearUsuario);
        btnLogin = findViewById(R.id.btnLogin);
        btnProducto = findViewById(R.id.btnProducto);

        //Añadimos un icono en ActionBar de la App
        actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.firebase32);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Listener para el Botón btnNuevoUsuario
        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent para pasar a UserActivity
                Intent intentUser = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intentUser);
            }
        });

        //Listener para el Botón btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Declaramos el Intent para pasar a LoginActivity
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });

        //Listener para el Botón btnProducto
        btnProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Declaramos la Activity para pasar a ProductosActivity
                Intent intentProductos = new Intent(MainActivity.this, ProductosActivity.class);
                startActivity(intentProductos);
            }
        });

    }
}
