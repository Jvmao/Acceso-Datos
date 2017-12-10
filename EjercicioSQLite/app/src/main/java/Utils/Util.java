package Utils;


public class Util {
    //Establecemos la versión de la BBDD
    public static final int DATABASE_VERSION=1;

    //Definimos el nombre de la BBDD
    public static final String DATABASE_NAME="instituto.db";
    public static final String DATABASE_ALUMNO="alumno";
    public static final String DATABASE_PROFESOR="profesor";

    //Definimos las Columnas de Alumno en la BBDD
    public static final String ID_ALUMNO="alumno_id";
    public static final String NOMBRE_ALUMNO="nombre_alumno";
    public static final String EDAD_ALUMNO="edad_alumno";
    public static final String CICLO_ALUMNO="ciclo_alumno";
    public static final String CURSO_ALUMNO="curso_alumno";
    public static final String NOTA_MEDIA="nota_media";

    //Definimos las Columnas de Profesor en la BBDD
    public static final String ID_PROFESOR="profesor_id";
    public static final String NOMBRE_PROFESOR="nombre_profesor";
    public static final String EDAD_PROFESOR="edad_profesor";
    public static final String CICLO_PROFESOR="ciclo_impartido";
    public static final String CURSO_PROFESOR="curso_tutor";
    public static final String DESPACHO="despacho";

}
