package Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Modelo.Alumno;
import Modelo.Profesor;
import Utils.Util;

public class DBHandler extends SQLiteOpenHelper{
    //Creamos la Tabla Alumno con sus correspondientes Columnas
    private String ALUMNO_TABLE="CREATE TABLE "+Util.DATABASE_ALUMNO+" ("
            +Util.ID_ALUMNO+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            +Util.NOMBRE_ALUMNO+" TEXT, "
            +Util.EDAD_ALUMNO+" TEXT, "
            +Util.CICLO_ALUMNO+" TEXT, "
            +Util.CURSO_ALUMNO+" TEXT, "
            +Util.NOTA_MEDIA+ " TEXT "+")";

    //Creamos la Tabla del Profesor con sus columnas
    private String PROFESOR_TABLE="CREATE TABLE "+Util.DATABASE_PROFESOR+" ("
            +Util.ID_PROFESOR+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            +Util.NOMBRE_PROFESOR+" TEXT, "
            +Util.EDAD_PROFESOR+" TEXT, "
            +Util.CICLO_PROFESOR+" TEXT, "
            +Util.CURSO_PROFESOR+" TEXT, "
            +Util.DESPACHO+ " TEXT "+")";

    //Generamos el Constructor desde Generate>>>Constructor
    public DBHandler(Context context) {
        //Pasamos el Nombre de la BBDD y la Versión
        super(context,Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ALUMNO_TABLE); //Se ejecuta la orden SQL
        db.execSQL(PROFESOR_TABLE); //Se ejecuta la orden SQL
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimina la tabla del Alumno si ya existe
        db.execSQL("DROP TABLE IF EXISTS "+Util.DATABASE_ALUMNO);
        //Elimina la tabla del Profesor si ya existe
        db.execSQL("DROP TABLE IF EXISTS "+Util.DATABASE_PROFESOR);
        //Creamos las tablas otra vez
        onCreate(db);
    }

    //Creamos el Método para Añadir un Alumno
    public void addAlumno(Alumno alumno){
        SQLiteDatabase db = this.getWritableDatabase();

        //Definimos el ContenValue para definir los Valores a Agregar en la BBDD
        ContentValues values = new ContentValues();
        //Agregamos los Valores de la BBDD
        //values.put(Util.ID_ALUMNO,alumno.getId());
        values.put(Util.NOMBRE_ALUMNO,alumno.getNombre());
        values.put(Util.EDAD_ALUMNO,alumno.getEdad());
        values.put(Util.CICLO_ALUMNO,alumno.getCiclo());
        values.put(Util.CURSO_ALUMNO,alumno.getCurso());
        values.put(Util.NOTA_MEDIA,alumno.getNota_media());

        //Le indicamos la tabla donde añadir los valores
        db.insert(Util.DATABASE_ALUMNO,null,values);

        Log.d("GUARDADO!!!","Datos Alumno Guardados!!!"+"ID: "+alumno.getId()+" NOMBRE: "+alumno.getNombre()
                +" EDAD: "+alumno.getEdad()+ " CICLO: "+alumno.getCiclo()+" CURSO: "+alumno.getCurso()+" MEDIA: "+alumno.getNota_media());
    }

    //Creamos el Método para Añadir un Profesor
    public void addProfesor(Profesor profesor){
        SQLiteDatabase db = this.getWritableDatabase();

        //Definimos el ContenValue para definir los Valores a Agregar en la BBDD
        ContentValues values = new ContentValues();
        //Agregamos los Valores de la BBDD
        //values.put(Util.ID_PROFESOR,profesor.getId());
        values.put(Util.NOMBRE_PROFESOR,profesor.getNombre());
        values.put(Util.EDAD_PROFESOR,profesor.getEdad());
        values.put(Util.CICLO_PROFESOR,profesor.getCiclo());
        values.put(Util.CURSO_PROFESOR,profesor.getCurso_tutor());
        values.put(Util.DESPACHO,profesor.getDespacho());

        //Le indicamos la tabla donde añadir los valores
        db.insert(Util.DATABASE_PROFESOR,null,values);

        Log.d("GUARDADO!!!","Datos Profesor Guardados>>>"+"ID: "+profesor.getId()+" NOMBRE: "+profesor.getNombre()
        +" EDAD: "+profesor.getEdad()+ " CICLO: "+profesor.getCiclo()+" CURSO: "+profesor.getCurso_tutor()+" DESPACHO: "+profesor.getDespacho());

    }

    //Eliminar elementos Alumno
    public void eliminarAlumno(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Alumno alumno = new Alumno();
        db.delete(Util.DATABASE_ALUMNO,Util.ID_ALUMNO+" = ?",
                new String[]{Integer.toString(id)});
        Log.d("ELIMINADO","Elemento Eliminado de la BBDD con ID = "+alumno.getId());
        db.close(); //Cerramos la BBDD
    }

    //Eliminar elementos Profesor
    public void eliminarProfesor(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Profesor profesor = new Profesor();
        db.delete(Util.DATABASE_PROFESOR,Util.ID_PROFESOR+" = ?",
                new String[]{String.valueOf(id)});
        Log.d("ELIMINADO","Elemento Eliminado de la BBDD con ID = "+profesor.getId());
        db.close(); //Cerramos la BBDD
    }


    //Creamos el Método para Listar todos los elementos Guardados de Alumno
    public List<Alumno> getAlumnos(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Alumno> alumnoList = new ArrayList<>();
        Cursor cursor=db.query(Util.DATABASE_ALUMNO,new String[]{
                        Util.ID_ALUMNO, Util.NOMBRE_ALUMNO, Util.EDAD_ALUMNO, Util.CICLO_ALUMNO,Util.CURSO_ALUMNO,Util.NOTA_MEDIA},
                null,null,null,null,Util.ID_ALUMNO+" DESC ");

        if(cursor != null && cursor.moveToFirst()){
            do {
                Alumno alumno = new Alumno();
                alumno.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.ID_ALUMNO))));
                alumno.setNombre(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_ALUMNO)));
                alumno.setEdad(cursor.getString(cursor.getColumnIndex(Util.EDAD_ALUMNO)));
                alumno.setCiclo(cursor.getString(cursor.getColumnIndex(Util.CICLO_ALUMNO)));
                alumno.setCurso(cursor.getString(cursor.getColumnIndex(Util.CURSO_ALUMNO)));
                alumno.setNota_media(cursor.getString(cursor.getColumnIndex(Util.NOTA_MEDIA)));

                //Añadimos a la lista
                alumnoList.add(alumno);
            }while (cursor.moveToNext());
        }
        return alumnoList;
    }

    //Creamos el Método para Listar todos los elementos Guardados de Profesor
    public List<Profesor> getProfesor(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Profesor> profesorList = new ArrayList<>();
        Cursor cursor=db.query(Util.DATABASE_PROFESOR,new String[]{
                        Util.ID_PROFESOR, Util.NOMBRE_PROFESOR, Util.EDAD_PROFESOR, Util.CICLO_PROFESOR,Util.CURSO_PROFESOR,Util.DESPACHO},
                null,null,null,null,Util.ID_PROFESOR+" DESC ");

        if(cursor != null && cursor.moveToFirst()){
            do {
                Profesor profesor = new Profesor();
                profesor.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.ID_PROFESOR))));
                profesor.setNombre(cursor.getString(cursor.getColumnIndex(Util.NOMBRE_PROFESOR)));
                profesor.setEdad(cursor.getString(cursor.getColumnIndex(Util.EDAD_PROFESOR)));
                profesor.setCiclo(cursor.getString(cursor.getColumnIndex(Util.CICLO_PROFESOR)));
                profesor.setCurso_tutor(cursor.getString(cursor.getColumnIndex(Util.CURSO_PROFESOR)));
                profesor.setDespacho(cursor.getString(cursor.getColumnIndex(Util.DESPACHO)));

                //Añadimos a la lista
                profesorList.add(profesor);
            }while (cursor.moveToNext());
        }
        return profesorList;
    }
}
