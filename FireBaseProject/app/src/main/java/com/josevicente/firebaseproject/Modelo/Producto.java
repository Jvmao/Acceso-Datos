package com.josevicente.firebaseproject.Modelo;

/**
 * Created by JoseVicente on 21/12/2017.
 */

public class Producto {
    //Declaramos las variables
    private String id;
    private String nproducto;
    private String desc;
    private String categoria;
    private String precio;
    private String fecha;

    private String imagen;

    //Constructor Vacío
    public Producto(){}

    //Constructor de las Variables
    public Producto(String id,String nproducto, String desc, String categoria, String precio, String fecha, String imagen) {
        this.id = id;
        this.nproducto = nproducto;
        this.desc = desc;
        this.categoria = categoria;
        this.precio = precio;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNproducto() {
        return nproducto;
    }

    public void setNproducto(String nproducto) {
        this.nproducto = nproducto;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
