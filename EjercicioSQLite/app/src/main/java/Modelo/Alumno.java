package Modelo;

/**
 * Created by JoseVicente on 29/11/2017.
 */

public class Alumno {
    private int id;
    private String nombre;
    private String edad;
    private String ciclo;
    private String curso;
    private String nota_media;

    //Constructor Vacío
    public Alumno(){}

    //Constructor Variables Alumno
    public Alumno(int id, String nombre, String edad, String ciclo, String curso, String nota_media) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.ciclo = ciclo;
        this.curso = curso;
        this.nota_media = nota_media;
    }

    //Getters and Setters Alumno
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }
    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCiclo() {
        return ciclo;
    }
    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNota_media() {
        return nota_media;
    }
    public void setNota_media(String nota_media) {
        this.nota_media = nota_media;
    }
}
