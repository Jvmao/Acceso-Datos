package com.josevicente.ejerciciosqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adaptador.AdaptadorAlumno;
import Adaptador.AdaptadorProfesor;
import Datos.DBHandler;
import Modelo.Alumno;
import Modelo.Profesor;

public class ElProfesorActivity extends AppCompatActivity {
    //Declaramos las Variables
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private List<Profesor> profesorList;
    private List<Profesor> listIems;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el_profesor);

        //Inicializamos las Variables e iniciamos el RecyclerView
        db = new DBHandler(this);
        recyclerView = findViewById(R.id.recyclerProfesorID); //Añadimos el recyclerView desde activity_el_profesor
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Instanciamos los Objetos
        profesorList = new ArrayList<>();
        listIems = new ArrayList<>();
        //Conseguimos los items desde la BBDD
        profesorList=db.getProfesor();

        for(Profesor p: profesorList){
            Profesor profesor = new Profesor();
            profesor.setNombre(p.getNombre()); //Obtenemos el nombre del profesor de la actividad anterior y los demás elementos
            profesor.setEdad((p.getEdad()));
            profesor.setCiclo(p.getCiclo());
            profesor.setCurso_tutor(p.getCurso_tutor());
            profesor.setDespacho(p.getDespacho());

            listIems.add(profesor); //Añadimos los elementos a la lista
        }

        //Instanciamos un nuevo recyclerViewAdapter
        recyclerViewAdapter = new AdaptadorProfesor(this,listIems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
