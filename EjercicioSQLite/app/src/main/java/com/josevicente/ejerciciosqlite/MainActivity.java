package com.josevicente.ejerciciosqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import Utils.Util;

public class MainActivity extends AppCompatActivity {
    private Button btnAlumno,btnProfesor, btnListado,btnDeleteAll;

    //Declaramos las Variables del Confirmation_Dialog
    private AlertDialog.Builder alertDialogBilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlumno=findViewById(R.id.btnAlumno);
        btnProfesor=findViewById(R.id.btnProfesor);
        btnListado =findViewById(R.id.btnListadoAll);
        btnDeleteAll=findViewById(R.id.borradoCompleto);

        //Pasamos al Activity del Alumno
        btnAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Agregamos los Intent para pasar a la siguiente Actividad
                Intent intentAlumno = new Intent(MainActivity.this,ActivityAlumno.class);
                startActivity(intentAlumno);
            }
        });

        //Pasamos al Activity del Profesor
        btnProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfesor = new Intent(MainActivity.this,ActivityProfesor.class);
                startActivity(intentProfesor);
            }
        });

        //Pasamos a la Activity Consultas
        btnListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentListado = new Intent(MainActivity.this,ListadoActivity.class);
                startActivity(intentListado);
            }
        });

        //Borramos la BBDD
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarTodo();
            }
        });
    }

    //Creamos el Método para el Borrado completo de la BBDD e Insertamos el AlertDialog
    public boolean eliminarTodo(){
        ctx = this;
        //Insertamos el AlertDialog
        alertDialogBilder = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.deletedb_dialog,null);

        //Introducimos los Botones
        Button noButton = view.findViewById(R.id.noDeleteButton);
        Button yesButton = view.findViewById(R.id.yesDeeleteButton);

        alertDialogBilder.setView(view);
        dialog = alertDialogBilder.create();
        dialog.show();

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); //Cierra el Diálogo
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.deleteDatabase(Util.DATABASE_NAME);
                Log.d("ELIMINADA BBDD","Nombre BBDD = "+Util.DATABASE_NAME);
                dialog.dismiss(); //Cuando finaliza las operaciones cierra el Dialog
            }
        });
        return true;
    }
}
