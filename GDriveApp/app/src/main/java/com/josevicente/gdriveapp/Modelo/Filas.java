package com.josevicente.gdriveapp.Modelo;

/**
 * Created by JoseVicente on 14/12/2017.
 */

public class Filas {
    private int id;
    private String name;

    //Constructor Vacío
    public Filas(){}

    //Constructor
    public Filas(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
