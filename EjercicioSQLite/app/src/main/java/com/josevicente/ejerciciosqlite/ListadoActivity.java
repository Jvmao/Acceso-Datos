package com.josevicente.ejerciciosqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adaptador.AdaptadorAlumno;
import Adaptador.AdaptadorListado;
import Datos.DBHandler;
import Modelo.Alumno;
import Modelo.Profesor;

public class ListadoActivity extends AppCompatActivity {
    //Declaramos las Variables
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private List<Alumno> alumnoList;
    private List<Profesor> profesorList;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        //Inicializamos las Variables e iniciamos el RecyclerView
        db = new DBHandler(this);
        recyclerView = findViewById(R.id.recyclerListadoID); //Añadimos el recyclerView desde activity_el_alumno.xml
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Instanciamos los Objetos Alumno
        alumnoList = new ArrayList<>();
        //Conseguimos los items desde la BBDD
        alumnoList=db.getAlumnos();

        //Instanciamos los Objetos Profesor
        profesorList = new ArrayList<>();
        profesorList = db.getProfesor();

        //Instanciamos un nuevo recyclerViewAdapter
        recyclerViewAdapter = new AdaptadorListado(this,alumnoList,profesorList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
