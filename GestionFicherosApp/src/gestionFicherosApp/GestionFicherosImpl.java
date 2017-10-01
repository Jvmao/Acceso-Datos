package gestionFicherosApp;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import gestionficheros.FormatoVistas;
import gestionficheros.GestionFicheros;
import gestionficheros.GestionFicherosException;
import gestionficheros.TipoOrden;

public class GestionFicherosImpl implements GestionFicheros{
	//Declaramos un Objeto de Tipo File
	private File carpetaDeTrabajo = null;
	// Matriz de Objetos para guardar el contenido de las carpetas
	private Object[][] contenido;

	// Definimos las Variables fila y columna
	private int filas = 0;
	private int columnas = 3;

	// Atributos para Ejecutar el Programa
	private FormatoVistas formatoVistas = FormatoVistas.NOMBRES;
	private TipoOrden ordenado = TipoOrden.DESORDENADO;
	
	// Definimos el Método para la Gestión de Ficheros
	public GestionFicherosImpl() {
		try {
			carpetaDeTrabajo = File.listRoots()[0]; // Devuelve un vector tipo File vinculado a la raíz del SO
		} catch (Exception e) {
			e.getMessage();
		}
		actualiza(); // Llamamos al método Actualiza()
	}

	// Definimos el Método para Actualizar Ficheros
	private void actualiza() {
		String[] ficheros = carpetaDeTrabajo.list(); // Obtenemos los nombres
		// Calcula el número de filas necesario
		filas = ficheros.length / columnas;
		// Condición
		if (filas * columnas < ficheros.length) {
			filas++; // Si hay resto necesitaremos una fila más
		}

		// Dimensionar la matriz contenido según los resultados
		contenido = new String[filas][columnas];
		// Rellenar contenido con los nombres obtenidos
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				int ind = j * columnas + i;
				if (ind < ficheros.length) {
					contenido[j][i] = ficheros[ind];
				} else {
					contenido[j][i] = "";
				}
			}
		}
	}
	
	
	@Override
	//Modificamos el método para que cuando pulsamos un botón nos devuelve al anterior directorio
	public void arriba() {
		// Evitamos que nos devuelva null
		if (carpetaDeTrabajo.getParentFile() != null) {
			// Devuelve el pathName del directorio padre
			carpetaDeTrabajo = carpetaDeTrabajo.getParentFile();
			actualiza(); // llamamos al método actializa()
		}
		
	}

	@Override
	//Método para Crear un Carpeta Nueva
	public void creaCarpeta(String arg0) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);
		// Manejo de Excepciones para crear una nueva carpeta
		if (file.mkdir()) {
			file.mkdir(); // Creamos una carpeta nueva
		} else if (file.exists()) {// Si no crea la carpeta -> lanzará una excepción
			throw new GestionFicherosException(
					"Alerta. No se puede crear " + file.getName() + ". El Directorio ya Existe.");
		}
		if (!file.exists()) {// Si no existe -> lanzará una excepción
			throw new GestionFicherosException(
					"Alerta. No se puede crear " + file.getName() + ". No Dispone de Permisos de Usuario");
		}
		if (!file.canRead()) { // Si no tiene permisos de lectura -> lanzará una excepción
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getName() + ". No hay Permisos de Lectura");
		}
		if (!file.canWrite()) { // Si no tiene permisos de escritura -> lanzará una excepción
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getName() + ". No hay Permisos de Lectura");
		}
		if (!file.canExecute()) { // Si no tiene permisos de ejecución -> lanzará una excepción
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getName() + ". No hay Permisos de Ejecución");
		}
		actualiza(); // Actualizamos el Contenido
		
	}

	@Override
	//Método para crear Ficheros Vacíos
	public void creaFichero(String arg0) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);
		if (file.exists()) {// Excepción que se produce si el fichero ya existe
			throw new GestionFicherosException(
					"Alerta. No se puede crear " + file.getName() + ". El Fichero ya Existe");
		}

		if (file.canWrite()) {// Excepción que se produce si no hay permisos de escritura
			throw new GestionFicherosException(
					"Alerta. No se puede modificar " + file.getName() + ". El Fichero No Tiene Permisos de Escritura");
		}

		if (file.canRead()) {// Excepción que se produce si no hay permisos de lectura
			throw new GestionFicherosException(
					"Alerta. No se puede modificar " + file.getName() + ". El Fichero No Tiene Permisos de Lectura");
		}

		if (file.canExecute()) {// Excepción que se produce si no hay permisos de ejecución
			throw new GestionFicherosException(
					"Alerta. No se puede modificar " + file.getName() + ". El Fichero No Tiene Permisos de Ejecución");
		}

		try {
			if (!file.exists()) {// Si el fichero no existe previamente, permite crear uno nuevo
				file.createNewFile(); // Crea un nuevo fichero//
			}
		} catch (IOException e) {// Si se produce algún error -> Lanza una Excepción
			e.printStackTrace(); // Imprime el error
			System.err.println(e.getMessage()); // Saca el Error por consola
			JOptionPane.showInputDialog("Se ha producido un Error al crear el Archivo"); // Mensaje a través de JOptionPane																			
		} catch (SecurityException se) {// Excepción de Seguridad
			se.getCause();
			JOptionPane.showInputDialog("Error de Seguridad");
		}
		actualiza(); // Actualiza el Contenido
	}

	@Override
	//Método que permite eliminar un Directorio o Fichero
	public void elimina(String arg0) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);
		if (file.exists()) { // Si el fichero existe -> permite eliminarlo
			file.delete(); // Elimina un fichero o carpeta existente//
		} else {// Lanza una excepción si el fichero no se ha podido eliminar
			throw new GestionFicherosException("Error. No se ha podido eliminar " + file.getName() + ".");
		}
		actualiza(); // Actualiza el Contenido
	}

	@Override
	//Permite seleccionar una carpeta y la muestra en la búsqueda
	public void entraA(String arg0) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);

		if (!file.isDirectory()) {// Si el directorio no existe -> lanazará una excepción
			throw new GestionFicherosException("Error. " + file.getAbsolutePath() + " No es un Directorio");
		} else if (!file.exists()) {
			throw new GestionFicherosException("Error. " + file.getName() + " No Existe");
		}

		// Controlamos los permisos de lectura, escritura y ejecución
		if (!file.canRead()) {// Si el fichero no tiene permisos de Lectura -> Lanzará una Excepción
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getName() + ". No hay Permisos de Lectura");
		} else if (!file.canWrite()) {// Si el fichero no tiene permisos de Escritura -> Lanzará una Excepción
			throw new GestionFicherosException(
					"Alerta. No se puede modificar " + file.getName() + ". No hay Permisos de Escritura");
		} else if (!file.canExecute()) {// Si el fichero no tiene permisos de Ejecución -> Lanzará una Excepción
			throw new GestionFicherosException(
					"Alerta. No se puede ejecutar " + file.getName() + ". No hay Permisos de Ejecución");
		}
		/* <<<Compromar Error Null>>> */
		carpetaDeTrabajo = file;
		actualiza();// Actualiza el contenido	
	}

	@Override
	//Modificamos el Método de Columnas
	public int getColumnas() {
		return columnas; //nos devuelve columnas
	}

	@Override
	//Modificamos el método getContenido que es el que devuelve el contenido de las carpetas
	public Object[][] getContenido() {
		return contenido;
	}

	@Override
	//Modificamos el método getDireccionCarpeta para que devuelva la ruta absoluta
	public String getDireccionCarpeta() {
		// Creamos la variable StringBuilder
		StringBuilder strBuilder = new StringBuilder();
		// Devuelve por pantalla el nombre de la carpeta donde se está situado
		strBuilder.append(carpetaDeTrabajo.getAbsolutePath());
		return strBuilder.toString();
	}

	@Override
	//Método para obtener el Espacio Disponible del Disco Duro
	public String getEspacioDisponibleCarpetaTrabajo() {
		// Creamos la variable StringBuilder
		StringBuilder strBuilder = new StringBuilder();
		// Pasamos los Bytes a formato decimal
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		try {
			// Obtenemos el espacio disponible para utilizar
			strBuilder.append(decimalFormat.format(carpetaDeTrabajo.getUsableSpace() / 1024) + " bytes");
		} catch (Exception e) {
			e.printStackTrace(); // Imprime cualquier error que se pueda producir
		}
		return strBuilder.toString();
	}

	@Override
	//Método para obtener el Espacio Total del Disco Duro
	public String getEspacioTotalCarpetaTrabajo() {
		// Creamos la variable StringBuilder
		StringBuilder strBuilder = new StringBuilder();
		// Pasamos los Bytes a formato decimal
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		try {
			// Obtenemos el espacio Total
			strBuilder.append(decimalFormat.format(carpetaDeTrabajo.getTotalSpace() / 1024) + " bytes");
		} catch (Exception e) {
			e.printStackTrace();// Imprime el error
		}
		return strBuilder.toString();
	}

	@Override
	//Modificamos el Método de Filas
	public int getFilas() {
		return filas; //Nos devuelve filas que pueden ser de escritura
	}

	@Override
	//Modificamos el método para que devuelva el atributo definido
	public FormatoVistas getFormatoContenido() {
		return formatoVistas;
	}

	///////////////////////////////////ACTIVIDAD 1A///////////////////////////////////////////////
	@Override
	//Método para obtener información del Directorio o Archivo Seleccionado
	public String getInformacion(String arg0) throws GestionFicherosException {
		// Creamos la variable StringBuilder para concatenar cadenas
		StringBuilder strBuilder = new StringBuilder();
		// Le pasamos la carpeta de trabajo
		File file = new File(carpetaDeTrabajo, arg0);
		// Cambiamos el Formato fecha para que sea legible
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Pasamos los Bytes a formato decimal
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");

		// Lanzamos una excepción en caso de que no exista el fichero
		if (!file.exists()) {
			throw new GestionFicherosException("Alerta." + file.getName() + " No Existe.");
		}

		// Lanzamos una Excepción en caso de que el archivo no tenga permisos de Lectura//
		if (!file.canRead()) { // Controlamos los permisos de lectura
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getAbsolutePath() + ". No hay Permisos de Lectura");
		}

		// Lanzamos una Excepción en caso de que el archivo no tenga permisos de Escritura//
		if (!file.canWrite()) { // Controlamos los permisos de escritura
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getAbsolutePath() + ". No hay Permisos de Escritura");
		}

		// Lanzamos una Excepción en caso de que el archivo no tenga permisos de Ejecución//
		if (!file.canExecute()) { // Controlamos los permisos de ejecución
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getAbsolutePath() + ". No hay Permisos de Ejecución");
		}

		// Título
		strBuilder.append("<---------INFORMACIÓN DEL SISTEMA-------->");
		strBuilder.append("\n");
		// Nombre
		strBuilder.append("Nombre: ");
		strBuilder.append(file.getName());
		strBuilder.append("\n");
		// Tipo (para saber si es un carpeta o fichero)
		strBuilder.append("Fichero: ");
		strBuilder.append(file.isFile());
		strBuilder.append("\n");
		// Fecha Última Modificación
		strBuilder.append("Última Modificación: ");
		strBuilder.append(dateFormat.format(file.lastModified()));
		strBuilder.append("\n");
		// Ubicación donde se encuentra el fichero o directorio
		strBuilder.append("Ubicación del Fichero: ");
		strBuilder.append(file.getAbsolutePath());
		strBuilder.append("\n");
		// Si es un fichero oculto
		strBuilder.append("Ficheros Ocultos: ");
		strBuilder.append(file.isHidden());
		strBuilder.append("\n");
		// Permisos Disponibles
		strBuilder.append("Permisos: ");
		strBuilder.append("Lectura: " + file.canRead() + ", Escritura: " + file.canWrite() 
							+ ", Ejecución: " + file.canExecute());
		strBuilder.append("\n");

		if (file.isFile()) { // Si es un fichero obtenemos la siguiente info
			// Tamaño Fichero
			strBuilder.append("Tamaño Fichero: ");
			strBuilder.append(decimalFormat.format(file.length() / 1024) + " bytes");
			strBuilder.append("\n");
		} else if (file.isDirectory()) { // Si es un directorio nos devuelve lo siguiente
			// Ficheros Totales dentro del Directorio
			strBuilder.append("Ficheros en el Directorio: ");
			strBuilder.append(file.listFiles().length);
			strBuilder.append("\n");
			// Espacio Disponible
			strBuilder.append("Espacio Disponible en Disco: ");
			strBuilder.append(decimalFormat.format(file.getUsableSpace() / 1024) + " bytes");
			strBuilder.append("\n");
			// Espacio Libre
			strBuilder.append("Espacio Libre en Disco: ");
			strBuilder.append(decimalFormat.format(file.getFreeSpace() / 1024) + " bytes");
			strBuilder.append("\n");
			// Espacio Total
			strBuilder.append("Espacio Total en Disco: ");
			strBuilder.append(decimalFormat.format(file.getTotalSpace() / 1024) + " bytes");
		}
		actualiza(); // Actualizamos el contenido
		return strBuilder.toString();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean getMostrarOcultos() {
		return carpetaDeTrabajo.isHidden(); //Muestra Carpetas Ocultas
	}

	@Override
	public String getNombreCarpeta() {
		return carpetaDeTrabajo.getName(); //Obtenemos el nombre de la carpeta
	}

	@Override
	//Modificamos el método para que devuelva el atributo definido
	public TipoOrden getOrdenado() {
		return ordenado; 
	}

	@Override
	public String[] getTituloColumnas() {
		return null;
	}

	@Override
	//Devuelve la última modificación del Fichero
	public long getUltimaModificacion(String arg0) throws GestionFicherosException {
		return carpetaDeTrabajo.lastModified(); 
	}

	@Override
	public String nomRaiz(int arg0) {
		return null;
	}

	@Override
	public int numRaices() {
		return 0;
	}

	//////////////////////////////---ACTIVIDAD 1B---///////////////////////////////////
	@Override
	//Método que permite renombrar el Fichero o Directorio
	public void renombra(String arg0, String arg1) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);
		File file1 = new File(carpetaDeTrabajo, arg1);
		if (file.exists()) {// Si el archivo existe
			file.renameTo(file1); // Renombramos el fichero
		} else {// Si el archivo no se puede modificar lanza una excepción
			throw new GestionFicherosException("Alerta. No se permite cambiar el nombre de " + file.getName() + ".");
		}
		actualiza(); // Actualizamos el contenido
	}
	///////////////////////////////////////////////////////////////////////////////////

	@Override
	//Método para saber si se puede ejecutar el archivo
	public boolean sePuedeEjecutar(String arg0) throws GestionFicherosException {
		return carpetaDeTrabajo.canExecute();
	}
	
	@Override
	//Método para saber si se puede escribir en el archivo
	public boolean sePuedeEscribir(String arg0) throws GestionFicherosException {
		return carpetaDeTrabajo.canWrite();
	}

	@Override
	//Método para saber si se puede leer el archivo
	public boolean sePuedeLeer(String arg0) throws GestionFicherosException {
		return carpetaDeTrabajo.canRead();
	}

	@Override
	//Modificamos el Método setColumnas
	public void setColumnas(int e) {
		columnas = e;
	}

	@Override
	//Modificamos el método para la busqueda de carpetas
	public void setDirCarpeta(String arg0) throws GestionFicherosException {
		File file = new File(arg0);
		carpetaDeTrabajo = file;
		// Gestionamos las excepciones para ver que la dirección existe y sea un
		// directorio
		if (!file.isDirectory()) {
			throw new GestionFicherosException(
					"Error. Se esperaba" + " un Directorio, pero " + file.getAbsolutePath() + " es un Fichero");
		}
		// Controlamos los Permisos de Lectura
		if (!file.canRead()) {
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getAbsolutePath() + ". No hay Permisos de Lectura");
		}
		// Controlamos los Permisos de Escritura
		if (!file.canWrite()) {
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getAbsolutePath() + ". No hay Permisos de Escritura");
		}
		// Controlamos los Permisos de Ejecución
		if (!file.canExecute()) {
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a " + file.getAbsolutePath() + ". No hay Permisos de Ejecución");
		}
		actualiza();// Actualiza el contenido
	}

	@Override
	public void setFormatoContenido(FormatoVistas arg0) {	
	}
	@Override
	public void setMostrarOcultos(boolean arg0) {
	}
	@Override
	public void setOrdenado(TipoOrden arg0) {
	}

	@Override
	//Método para establcer permisos de Ejecución
	public void setSePuedeEjecutar(String arg0, boolean arg1) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);
		if (file.exists()) {
			boolean ejecutable = file.setExecutable(true); // El archivo se puede ejecutar
		} else {
			throw new GestionFicherosException( // El archivo no tiene permisos de ejecución
					"Alerta. No se puede ejecutar " + file.getName() + ". No hay Permisos de Ejecución");
		}
		actualiza(); // Actualiza el contenido
	}

	@Override
	//Método para establecer permisos de Escritura
	public void setSePuedeEscribir(String arg0, boolean arg1) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);
		if (file.exists()) {
			boolean escritura = file.setWritable(true); // El archivo tiene permisos de escritura
		} else {
			throw new GestionFicherosException( // El archivo no tiene permisos de escritura
					"Alerta. No se puede ejecutar " + file.getName() + ". No hay Permisos de Escritura");
		}
		actualiza(); // Actualiza el Contenido
	}

	@Override
	//Método para establecer permisos de Lectura
	public void setSePuedeLeer(String arg0, boolean arg1) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo,arg0);
		if(file.exists()){
			boolean lectura = file.setReadable(true); //El archivo tiene permisos de lectura
		}else{
			throw new GestionFicherosException( //El archivo no tiene permisos de lectura
					"Alerta. No se puede leer " +file.getName()
					+ ". No hay Permisos de Lectura"
				);
		}
		actualiza(); //Actualiza el Contenido	
	}

	@Override
	//Método para establecer la fecha de la última modificación
	public void setUltimaModificacion(String arg0, long arg1) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo,arg0);
		if(file.exists()){
			Calendar fecha = Calendar.getInstance();
			long ultimaMod = fecha.getTimeInMillis();
			file.setLastModified(ultimaMod);
		}
		actualiza(); //Actualizamos el contenido
	}

}
