package Marshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

public class Marshaller_Libro {

	public static void main(String[] args) throws TransformerException {
		ArrayList<Libro> libros = null;
		Scanner kbd = new Scanner(System.in);
		String titulo,autor,editor;
		int id,year,pag;
		
		try{
		//Introducimos los Datos Manualmente desde la clase scanner
		System.out.println("Introduce ID: ");
		id = kbd.nextInt();
		System.out.println("Introduce el T�tulo: ");
		titulo = kbd.next();
		System.out.println("Introduce el Nombre del Autor: ");
		autor = kbd.next();
		System.out.println("Introduce el A�o de Edici�n: ");
		year = kbd.nextInt();
		System.out.println("Introduce el Nombre del Editor: ");
		editor = kbd.next();
		System.out.println("Introduce el N�mero de P�ginas: ");
		pag = kbd.nextInt();
		
		//Cargar los datos
		libros = new ArrayList<Libro>();
		libros.add(new Libro(id,titulo,autor,year,editor,pag));
		Marshaller marshaller = new Marshaller(libros);
		marshaller.crearDocumento();
		marshaller.crearArbolDOM();
		
		kbd.close(); //Cerramos Scanner
		
		File file = new File("libros.xml");//Definimos el nombre del Fichero creado
		System.out.println("Archivo creado con �xito en la Ruta: "+file.getAbsolutePath()); //Informaci�n sobre la ruta del fichero creado
		marshaller.escribirDocumentAXml(file); //Parseamos el archivo a XML	
		
		}catch(InputMismatchException ex){
			System.err.println("Error Mismatch: "+ex);
		}
	}
}
