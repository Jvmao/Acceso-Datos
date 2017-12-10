package com.josevicente.ejerciciosqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Datos.DBHandler;
import Modelo.Alumno;
import Utils.Util;

public class ActivityAlumno extends AppCompatActivity {
    //Declaramos las Variables
    private Button btnGuardarAlumno, btnNombreAlumno,btnEdadAlumno,btnccAlumno,
    btnCicloAlumno,btnCursoAlumno,btnNotaAlumno,btnListar,btnEliminar,btnLimpiar;
    private TextView idAlumno;
    private EditText nombre;
    private EditText edad;
    private EditText ciclo;
    private EditText curso;
    private EditText notaM;
    private DBHandler db;

    private ListView listViewAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        //Instanciamos los Elementos
        idAlumno = findViewById(R.id.idAlumno);
        nombre = findViewById(R.id.nombreAlumno);
        edad = findViewById(R.id.edadAlumno);
        ciclo = findViewById(R.id.cicloAlumno);
        curso = findViewById(R.id.cursoAlumno);
        notaM = findViewById(R.id.notaAlumno);
        btnGuardarAlumno = findViewById(R.id.btnGuardarAlumno);
        btnNombreAlumno = findViewById(R.id.btnNombreAlumno);
        btnEdadAlumno=findViewById(R.id.btnEdadAlumno);
        btnccAlumno=findViewById(R.id.btnccAlumno);
        btnCicloAlumno=findViewById(R.id.btnCicloAlumno);
        btnCursoAlumno=findViewById(R.id.btnCursoAlumno);
        btnNotaAlumno=findViewById(R.id.btnMediaAlumno);
        btnListar = findViewById(R.id.btnListarAlumno);
        btnEliminar = findViewById(R.id.btnEliminarAlumno);
        btnLimpiar = findViewById(R.id.btnLimpiarAlumno);
        db = new DBHandler(this);
        listViewAlumno = findViewById(R.id.listViewAlumno);

        //Botón para Guardar los Datos del Alumno
        btnGuardarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent(v);
            }
        });

        //Botón para Buscar alumnos por Nombre
        btnNombreAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreAlumno(v);
            }
        });

        //Botón para Buscar alumnos por Edad
        btnEdadAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edadAlumno(v);
            }
        });

        //Botón para Buscar por Ciclo y Curso
        btnccAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursoCiclo(v);
            }
        });

        //Botón para Buscar alumnos por Ciclo
        btnCicloAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cicloAlumno(v);
            }
        });
        //Botón para Buscar alumnos por Curso
        btnCursoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursoAlumno(v);
            }
        });
        //Botón para Buscar alumnos por Nota
        btnNotaAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notaAlumno(v);
            }
        });

        //Botón para Listar todos los Elementos ALUMNO, guardados en la BBDD
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarAlumno(v);
            }
        });

        //Botón para eliminar un elemento de la BBDD
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar_alumno(v);
            }
        });

        //Botón para Limpiar todos los Campos de Texto
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarAlumno(v); //Limpiamos todos los Campos
                db.close();
            }
        });

    }

    //Creamos el Método para guardar los Datos del Alumno
    public void saveStudent(View v) {
        //Creamos un nuevo objeto Alumno
        Alumno alumno = new Alumno();

        //Añadimos los elementos para ser recogidos por la BBDD
        String name = nombre.getText().toString();
        alumno.setNombre(name);
        String age = edad.getText().toString();
        alumno.setEdad(age);
        String cycle = ciclo.getText().toString();
        alumno.setCiclo(cycle);
        String course = curso.getText().toString();
        alumno.setCurso(course);
        String average = notaM.getText().toString();
        alumno.setNota_media(average);

        if(!name.isEmpty() && !age.isEmpty() && !cycle.isEmpty() && !course.isEmpty() && !average.isEmpty()){
            //Guardamos el Objeto en la BBDD
            db.addAlumno(alumno);
            Toast.makeText(ActivityAlumno.this, "DATOS GUARDADOS", Toast.LENGTH_LONG).show();
            //Intent para pasar a ElAlumnoActivity
            startActivity(new Intent(ActivityAlumno.this, ElAlumnoActivity.class));
        }else{
            Toast.makeText(ActivityAlumno.this, "FALTAN CAMPOS POR RELLENAR", Toast.LENGTH_LONG).show();
        }
        db.close();
    }


    //Creamos el Método para Realizar las búsquedas en la BBDD por Nombre
    public void nombreAlumno(View v) {
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String n = nombre.getText().toString(); //Creamos una nueva variable para recoger el nombre en el campo indicado
        //Definimos la sentencia SQL para realizar la búsqueda por NOMBRE
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_ALUMNO + " WHERE "
                + Util.NOMBRE_ALUMNO + "='" + n + "'", null);

        //El cursor deberá de leer los siguientes campos
        if (curso != null && cursor.moveToNext()) {
                idAlumno.setText(cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO)));
                nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))); //Indicamos las columnas a buscar
                edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO)));
                ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO)));
                curso.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO)));
                notaM.setText(cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA)));

                String[] columnasAlumno = {cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA))};
                ArrayAdapter<String> itemAlumno = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasAlumno);
                listViewAlumno.setAdapter(itemAlumno);

                /*Otra forma de listar los objetos
                ArrayList<String> values = new ArrayList<>();
                values.add(cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA)));
                ArrayAdapter<String> itemAlumno = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,values);
                listViewAlumno.setAdapter(itemAlumno);*/

                Toast.makeText(ActivityAlumno.this, "DATOS RECUPERADOS POR NOMBRE", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityAlumno.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarAlumno(v); //Limpiamos todos los Campos
        }

        db.close();
    }

    //Método para realizar las búsquedas por Edad del Alumno
    public void edadAlumno(View view) {
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String e = edad.getText().toString(); //Creamos una nueva variable para recoger la edad en el campo indicado

        //Definimos la sentencia SQL para realizar la búsqueda por EDAD
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_ALUMNO + " WHERE "
                + Util.EDAD_ALUMNO + "='" + e + "'", null);

        //El cursor deberá de encontrar los siguientes campos
        if (cursor.moveToNext()) {
            do {
                //Indicamos los datos mostrar en los editText
                idAlumno.setText(cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO)));
                nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO)));
                edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO)));
                ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO)));
                curso.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO)));
                notaM.setText(cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA)));

                String[] columnasAlumno = {cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO))+"\n"
                        +cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA))};
                ArrayAdapter<String> itemAlumno = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasAlumno);
                listViewAlumno.setAdapter(itemAlumno);

                Toast.makeText(ActivityAlumno.this, "DATOS RECUPERADOS POR EDAD", Toast.LENGTH_LONG).show();
            }while (cursor.moveToNext());
        } else {
            Toast.makeText(ActivityAlumno.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarAlumno(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para Buscar Datos por Ciclo
    public void cicloAlumno(View view){
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String c = ciclo.getText().toString(); //Creamos una nueva variable para recoger el ciclo en el campo indicado

        //Definimos la sentencia SQL para realizar la búsqueda por CICLO
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_ALUMNO + " WHERE "
                + Util.CICLO_ALUMNO + "='" + c + "'", null);

        //El cursor deberá de encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idAlumno.setText(cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO)));
            curso.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO)));
            notaM.setText(cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA)));

            String[] columnasAlumno = {cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA))};
            ArrayAdapter<String> itemAlumno = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasAlumno);
            listViewAlumno.setAdapter(itemAlumno);

            Toast.makeText(ActivityAlumno.this, "DATOS RECUPERADOS POR CICLO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityAlumno.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarAlumno(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para Buscar Datos por Curso
    public void cursoAlumno(View view){
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String cu = curso.getText().toString(); //Creamos una nueva variable para recoger el curso en el campo indicado

        //Definimos la sentencia SQL para realizar la búsqueda por CURSO
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_ALUMNO + " WHERE "
                + Util.CURSO_ALUMNO + "='" + cu + "'", null);

        //El cursor deberá encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idAlumno.setText(cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO)));
            curso.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO)));
            notaM.setText(cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA)));

            String[] columnasAlumno = {cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA))};
            ArrayAdapter<String> itemAlumno = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasAlumno);
            listViewAlumno.setAdapter(itemAlumno);

            Toast.makeText(ActivityAlumno.this, "DATOS RECUPERADOS POR CURSO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityAlumno.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarAlumno(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para buscar por curso y ciclo
    public void cursoCiclo(View view){
        SQLiteDatabase sdb = db.getReadableDatabase();
        String cic = ciclo.getText().toString();
        String cur = curso.getText().toString();

        //Definimos la sentencia SQL para realizar la búsqueda por CICLO y CURSO del Alumno
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_ALUMNO + " WHERE "
                + Util.CICLO_ALUMNO + "='" + cic + "' AND "+Util.CURSO_ALUMNO+"= '"+cur+ "'", null);

        //El cursor deberá encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idAlumno.setText(cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO)));
            curso.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO)));
            notaM.setText(cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA)));

            String[] columnasAlumno = {cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA))};
            ArrayAdapter<String> itemAlumno = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasAlumno);
            listViewAlumno.setAdapter(itemAlumno);

            Toast.makeText(ActivityAlumno.this, "DATOS RECUPERADOS POR CICLO Y CURSO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityAlumno.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarAlumno(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para Buscar Datos por Nota Media
    public void notaAlumno(View view){
        SQLiteDatabase sdb = db.getReadableDatabase(); //Declaramos la Base de Datos para que se legible
        String n = notaM.getText().toString(); //Creamos una nueva variable para recoger la nota media en el campo indicado

        //Definimos la sentencia SQL para realizar la búsqueda por NOTA MEDIA
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + Util.DATABASE_ALUMNO + " WHERE "
                + Util.NOTA_MEDIA + "='" + n + "'", null);

        //El cursor deberá encontrar los siguientes campos
        if (cursor.moveToNext()) {
            idAlumno.setText(cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO)));
            nombre.setText(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))); //Indicamos las columnas a buscar
            edad.setText(cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO)));
            ciclo.setText(cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO)));
            curso.setText(cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO)));
            notaM.setText(cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA)));

            String[] columnasAlumno = {cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO))+"\n"
                    +cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA))};
            ArrayAdapter<String> itemAlumno = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,columnasAlumno);
            listViewAlumno.setAdapter(itemAlumno);

            Toast.makeText(ActivityAlumno.this, "DATOS RECUPERADOS POR NOTA MEDIA", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ActivityAlumno.this, "DATOS NO ENCONTRADOS", Toast.LENGTH_LONG).show();
            limpiarAlumno(view); //Limpiamos todos los Campos
        }
        db.close();
    }

    //Método para Listar todos los Elementos de ALUMNO
    public void listarAlumno(View view){
        db.getAlumnos();
        db.close();
        Toast.makeText(ActivityAlumno.this,"MOSTRANDO DATOS",Toast.LENGTH_LONG).show();
        //Intent para pasar a ElAlumnoActivity
        startActivity(new Intent(ActivityAlumno.this,ElAlumnoActivity.class));
    }

    //Método para Eliminar Elementos de la BBDD ALUMNOS
    public void eliminar_alumno(View view){
        final int id = Integer.parseInt(idAlumno.getText().toString()); //Creamos una nueva variable para recoger la id en el campo indicado

        if(id !=0){
            db.eliminarAlumno(id);
            Toast.makeText(ActivityAlumno.this,"Eliminando Elemento con ID = "+id,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(ActivityAlumno.this,"No es Posible Eliminar Elemento",Toast.LENGTH_LONG).show();
        }
        limpiarAlumno(view);
        db.close();
    }


    //Método para Limpiar todos los Campos de Texto
    public void limpiarAlumno(View view){
        idAlumno.setText(" ");
        nombre.setText(" ");
        edad.setText(" ");
        ciclo.setText(" ");
        curso.setText(" ");
        notaM.setText(" ");
    }

}
