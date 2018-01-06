package com.josevicente.firebaseproject.Modelo;

/**
 * Created by JoseVicente on 14/12/2017.
 */

public class User {
    //Declaramos las Variables
    private String id;
    private String userName;
    private String pass;
    private String correo;
    private String nombre;
    private String apellidos;
    private String direc;

    //Constructor Vacío
    public User(){}

    //Constructor de las Variables
    public User(String id, String userName, String correo,String pass, String nombre, String apellidos, String direc) {
        this.id = id;
        this.userName = userName;
        this.pass = pass;
        this.correo = correo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direc = direc;
    }

    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDirec() {
        return direc;
    }

    public void setDirec(String direc) {
        this.direc = direc;
    }
}
