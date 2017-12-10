package com.josevicente.ejerciciosqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Datos.DBHandler;
import Modelo.Profesor;
import Utils.Util;

public class ActivityProfesor extends AppCompatActivity {
    //Declaramos las Variables
    private Button btnGuardarProfesor,btnNombreProfesor,btnEdadProfesor,btnccProfesor,
            btnCicloProfesor,btnCursoProfesor,btnDespacho,btnListarProfesor,btnEliminarProfesor,
            btnLimpiarProfesor;
    private TextView idProfesor;
    private EditText nombre;
    private EditText edad;
    private EditText ciclo;
    private EditText cursoT;
    private EditText despacho;
    private DBHandler db;

    private ListView listViewProfesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        //Instanciamos las Variables
        db = new DBHandler(this);
        idProfesor = findViewById(R.id.idProfesor);
        nombre = findViewById(R.id.nombreProfesor);
        edad = findViewById(R.id.edadProfesor);
        ciclo = findViewById(R.id.cicloProfesor);
        cursoT = findViewById(R.id.cursoProfesor);
        despacho = findViewById(R.id.despacho);
        btnGuardarProfesor = findViewById(R.id.btnGuradarProfesor);
        btnNombreProfesor = findViewById(R.id.btnNombreProfesor);
        btnEdadProfesor = findViewById(R.id.btnEdadProfesor);
        btnccProfesor = findViewById(R.id.btnccProfesor);
        btnCicloProfesor = findViewById(R.id.btnCicloProfesor);
        btnCursoProfesor = findViewById(R.id.btnCursoProfesor);
        btnDespacho = findViewById(R.id.btnDespacho);
        btnListarProfesor = findViewById(R.id.btnListarProfesor);
        btnEliminarProfesor = findViewById(R.id.btnEliminarProfesor);
        btnLimpiarProfesor = findViewById(R.id.btnLimpiarProfesor);
        listViewProfesor = findViewById(R.id.listViewProfesor);

        //Botón para guardar un profesor
        btnGuardarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeacher(v);
            }
        });

        //Botón para Buscar profesor por Nombre
        btnNombreProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreProfesor(v); //Añadimos el método creado para buscar por nombre
            }
        });

        //Botón para Buscar profesor por Edad
        btnEdadProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edadProfesor(v); //Método que busca por edad
            }
        });

        //Botón para Buscar por Ciclo y Curso
        btnccProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursoCicloProfesor(v);
            }
        });

        //Botón para buscar por ciclo
        btnCicloProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cicloProfesor(v);
            }
        });

        //Botón para buscar por curso
        btnCursoProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursoProfesor(v);
            }
        });

        //Botón para buscar por Despacho
        btnDespacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                despachoProfesor(v);
            }
        });

        //Botón para Listar profesor
        btnListarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarProfesor(v);
            }
        });

        //Botón para Eliminar el elemento
        btnEliminarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar_profesor(v);
            }
        });

        //Botón para limpiar los campos
        btnLimpiarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarProfesor(v);
                db.close();
            }
        });
    }

    //Creamos el Método para guardar los Datos del Profesor
    public void saveTeacher(View v){
        //Creamos un nuevo objeto Profesor
        Profesor profesor = new Profesor();

        //Añadimos los elementos para ser recogidos por la BBDD
        String name = nombre.getText().toString();
        profesor.setNombre(name);
        String age = edad.getText().toString();
        profesor.setEdad(age);
        String cycle = ciclo.getText().toString();
        profesor.setCiclo(cycle);
        String course = cursoT.getText().toString();
        profesor.setCurso_tutor(course);
        String office = despacho.getText().toString();
        profesor.setDespacho(office);

        if(!name.isEmpty() && !age.isEmpty() && !cycle.isEmpty() && !course.isEmpty() && !office.isEmpty()){
            //Guardamos el Objeto en la BBDD
            db.addProfesor(profesor);
            Toast.makeText(ActivityProfesor.this,"DATOS GUARDADOS",Toast.LENGTH_LONG).show();
            //Intent para pasar a ElProfesorActivity
            startActivity(new Intent(ActivityProfesor.this,ElProfesorActivity.class));
        }else{
            Toast.makeText(ActivityProfesor.this,"FALTAN CAMPOS POR RELLENAR",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    //Creamos el Método para Realizar las búsquedas en la BBDD por Nombre
    public void nombreProfesor(View v) {
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String n = nombre.getText().toString(); //Creamos una nueva variable para recoger el nombre en el campo indicado
        Profesor profesor = new Profesor();
        //Definimos la sentencia SQL para realizar la búsqueda por NOMBRE
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_PROFESOR + " WHERE "
                + Util.NOMBRE_PROFESOR + "= '" + n + "'", null);

        //El cursor deberá de encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idProfesor.setText(cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR)));
            cursoT.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR)));
            despacho.setText(cursor.getString(cursor.getColumnIndex(Util.DESPACHO)));

            //Definimos los datos a recoger por el String
            String[] columnasProfesor = {cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.DESPACHO))};
            //Añadimos los datos del String al ListView
            ArrayAdapter<String> itemProfesor = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasProfesor);
            listViewProfesor.setAdapter(itemProfesor);

            Toast.makeText(ActivityProfesor.this, "DATOS RECUPERADOS POR NOMBRE", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityProfesor.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarProfesor(v); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para realizar las búsquedas por Edad del Profesor
    public void edadProfesor(View view) {
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String e = edad.getText().toString(); //Creamos una nueva variable para recoger la edad en el campo indicado

        //Definimos la sentencia SQL para realizar la búsqueda por EDAD
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_PROFESOR + " WHERE "
                + Util.EDAD_PROFESOR + "='" + e + "'", null);

        //El cursor deberá de encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idProfesor.setText(cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR)));
            cursoT.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR)));
            despacho.setText(cursor.getString(cursor.getColumnIndex(Util.DESPACHO)));

            //Definimos los datos a recoger por el String
            String[] columnasProfesor = {cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.DESPACHO))};
            //Añadimos los datos del String al ListView
            ArrayAdapter<String> itemProfesor = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasProfesor);
            listViewProfesor.setAdapter(itemProfesor);

            Toast.makeText(ActivityProfesor.this, "DATOS RECUPERADOS POR EDAD", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityProfesor.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarProfesor(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para Buscar Datos por Ciclo
    public void cicloProfesor(View view){
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String c = ciclo.getText().toString(); //Creamos una nueva variable para recoger el ciclo en el campo indicado

        //Definimos la sentencia SQL para realizar la búsqueda por CICLO
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_PROFESOR + " WHERE "
                + Util.CICLO_PROFESOR + "='" + c + "'", null);

        //El cursor deberá de encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idProfesor.setText(cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR)));
            cursoT.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR)));
            despacho.setText(cursor.getString(cursor.getColumnIndex(Util.DESPACHO)));

            //Definimos los datos a recoger por el String
            String[] columnasProfesor = {cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.DESPACHO))};
            //Añadimos los datos del String al ListView
            ArrayAdapter<String> itemProfesor = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasProfesor);
            listViewProfesor.setAdapter(itemProfesor);

            Toast.makeText(ActivityProfesor.this, "DATOS RECUPERADOS POR CICLO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityProfesor.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarProfesor(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para Buscar Datos por Curso
    public void cursoProfesor(View view){
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String cu = cursoT.getText().toString(); //Creamos una nueva variable para recoger el curso en el campo indicado

        //Definimos la sentencia SQL para realizar la búsqueda por CURSO
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_PROFESOR + " WHERE "
                + Util.CURSO_PROFESOR + "='" + cu + "'", null);

        //El cursor deberá encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idProfesor.setText(cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR)));
            cursoT.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR)));
            despacho.setText(cursor.getString(cursor.getColumnIndex(Util.DESPACHO)));

            //Definimos los datos a recoger por el String
            String[] columnasProfesor = {cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.DESPACHO))};
            //Añadimos los datos del String al ListView
            ArrayAdapter<String> itemProfesor = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasProfesor);
            listViewProfesor.setAdapter(itemProfesor);

            Toast.makeText(ActivityProfesor.this, "DATOS RECUPERADOS POR CURSO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityProfesor.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarProfesor(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para buscar por curso y ciclo
    public void cursoCicloProfesor(View view){
        SQLiteDatabase sdb = db.getReadableDatabase();
        String cic = ciclo.getText().toString();
        String cur = cursoT.getText().toString();

        //Definimos la sentencia SQL para realizar la búsqueda por CICLO y CURSO del Profesor
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_PROFESOR + " WHERE "
                + Util.CICLO_PROFESOR + "='" + cic + "' AND "+Util.CURSO_PROFESOR+"= '"+cur+ "'", null);

        //El cursor deberá encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idProfesor.setText(cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR)));
            cursoT.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR)));
            despacho.setText(cursor.getString(cursor.getColumnIndex(Util.DESPACHO)));

            //Definimos los datos a recoger por el String
            String[] columnasProfesor = {cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.DESPACHO))};
            //Añadimos los datos del String al ListView
            ArrayAdapter<String> itemProfesor = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasProfesor);
            listViewProfesor.setAdapter(itemProfesor);

            Toast.makeText(ActivityProfesor.this, "DATOS RECUPERADOS POR CICLO Y CURSO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityProfesor.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarProfesor(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para Buscar Datos por Despacho
    public void despachoProfesor(View view){
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String d = despacho.getText().toString(); //Creamos una nueva variable para recoger el despacho en el campo indicado

        //Definimos la sentencia SQL para realizar la búsqueda por NOTA MEDIA
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_PROFESOR + " WHERE "
                + Util.DESPACHO + "='" + d + "'", null);

        //El cursor deberá encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idProfesor.setText(cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR)));
            cursoT.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR)));
            despacho.setText(cursor.getString(cursor.getColumnIndex(Util.DESPACHO)));

            //Definimos los datos a recoger por el String
            String[] columnasProfesor = {cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.DESPACHO))};
            //Añadimos los datos del String al ListView
            ArrayAdapter<String> itemProfesor = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasProfesor);
            listViewProfesor.setAdapter(itemProfesor);

            Toast.makeText(ActivityProfesor.this, "DATOS RECUPERADOS POR DESPACHO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityProfesor.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarProfesor(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para Listar todos los Elementos de PROFESOR
    public void listarProfesor(View view){
        //Guardamos el Objeto en la BBDD
        db.getProfesor();
        db.close();
        Toast.makeText(ActivityProfesor.this,"MOSTRANDO DATOS",Toast.LENGTH_LONG).show();
        //Intent para pasar a ElAlumnoActivity
        startActivity(new Intent(ActivityProfesor.this,ElProfesorActivity.class));
    }

    //Método para Eliminar Elementos de la BBDD ALUMNOS
    public void eliminar_profesor(View view){
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        final int id = Integer.parseInt(idProfesor.getText().toString()); //Creamos una nueva variable para recoger la id en el campo indicado

        if(id !=0){
            db.eliminarProfesor(id);
            Toast.makeText(ActivityProfesor.this,"Eliminando Elemento con ID = "+id,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(ActivityProfesor.this,"No es Posible Eliminar Elemento",Toast.LENGTH_LONG).show();
        }
        limpiarProfesor(view);
        db.close();
    }

    //Método para Limpiar todos los Campos de Texto
    public void limpiarProfesor(View view){
        idProfesor.setText(" ");
        nombre.setText(" ");
        edad.setText(" ");
        ciclo.setText(" ");
        cursoT.setText(" ");
        despacho.setText(" ");
    }


}
