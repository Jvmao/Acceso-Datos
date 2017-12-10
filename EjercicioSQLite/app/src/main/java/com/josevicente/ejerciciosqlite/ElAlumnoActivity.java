package com.josevicente.ejerciciosqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adaptador.AdaptadorAlumno;
import Datos.DBHandler;
import Modelo.Alumno;


public class ElAlumnoActivity extends AppCompatActivity {
    //Declaramos las Variables
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private List<Alumno> alumnoList;
    private List<Alumno> listIems;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el_alumno);

        //Inicializamos las Variables e iniciamos el RecyclerView
        db = new DBHandler(this);
        recyclerView = findViewById(R.id.recyclerAlumnoID); //Añadimos el recyclerView desde activity_el_alumno.xml
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Instanciamos los Objetos
        alumnoList = new ArrayList<>();
        listIems = new ArrayList<>();
        //Conseguimos los items desde la BBDD
        alumnoList=db.getAlumnos();

        for(Alumno a: alumnoList){
            Alumno alumno = new Alumno();
            alumno.setNombre(a.getNombre()); //Obtenemos el nombre del alumno de la actividad anterior
            alumno.setEdad((a.getEdad()));
            alumno.setCiclo(a.getCiclo());
            alumno.setCurso(a.getCurso());
            alumno.setNota_media(a.getNota_media());

            listIems.add(alumno); //Añadimos los elementos a la lista
        }

        //Instanciamos un nuevo recyclerViewAdapter
        recyclerViewAdapter = new AdaptadorAlumno(this,listIems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
