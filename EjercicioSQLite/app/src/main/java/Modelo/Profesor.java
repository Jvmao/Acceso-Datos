package Modelo;


public class Profesor {
    private int id;
    private String nombre;
    private String edad;
    private String ciclo;
    private String curso_tutor;
    private String despacho;

    //Constructor Vacío
    public Profesor(){}

    //Constructor Variables Profesor
    public Profesor(int id, String nombre, String edad, String ciclo, String curso_tutor, String despacho) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.ciclo = ciclo;
        this.curso_tutor = curso_tutor;
        this.despacho = despacho;
    }

    //Getters and Setters de Profesor
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

    public String getCurso_tutor() {
        return curso_tutor;
    }
    public void setCurso_tutor(String curso_tutor) {
        this.curso_tutor = curso_tutor;
    }

    public String getDespacho() {
        return despacho;
    }
    public void setDespacho(String despacho) {
        this.despacho = despacho;
    }
}
