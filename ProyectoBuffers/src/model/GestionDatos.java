package model;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GestionDatos {
	//Variables
	private String fichero1 = "texto1.txt";
	private String fichero2 = "texto2.txt";
	private FileReader file1,file2;
	private BufferedReader buf1,buf2;
	private int contador1 =0;
	private int contador2 =0;

	private FileInputStream fi;
	private FileOutputStream fo;
	private DataOutputStream size;
	
	public GestionDatos() { //Constructor Vacío
	}

	//TODO: Implementa una función para abrir ficheros
	public void openFile() throws IOException{
		file1 = new FileReader(fichero1); //Definimos FileReader para el fichero 1
		buf1 = new BufferedReader(file1); //BufferedReader para el fichero 1
		file2 = new FileReader(fichero2); //Definimos FileReader para el fichero 2
		buf2 = new BufferedReader(file2); //BufferedReader para el fichero 2
	}
	
	//TODO: Implementa una función para cerrar ficheros
	public void closeFile() throws IOException{
		buf1.close(); //Cierra el Fichero1
		buf2.close(); //Cierra el Fichero2
	}
	
	//Método para abrir los archivos a copiar
	public void openCopy(){
		try {
			fi = new FileInputStream(""); //Definimos el Archivo de Entrada
			fo = new FileOutputStream(""); //Definimos el Archivo de Salida
			size = new DataOutputStream(fo); //Definimos el tamaño del Archivo
			byte[] bytes = new byte[20000]; //Especificamos los bytes del nuevo archivo
			
			fi.read(bytes);
			fo.write(bytes);
			size.writeDouble(5.5);
		} catch (FileNotFoundException e){
			e.printStackTrace();
			e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			e.getMessage();
		}finally{ //Cerramos los Archivos
			closeCopy(fi);
			closeCopy(fo);
			closeCopy(size);
		}
	}
	
	//Método para Cerrar los Archivos Creados
	public void closeCopy(Closeable c){
		try {
			c.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Definimos el Método para comparar el contenido de los ficheros
	public boolean compararContenido (String fichero1,String fichero2) throws IOException{
		//TODO: Implementa la función
		openFile(); //Llamamos al Método para abrir los Ficheros
		while(fichero1 != null && fichero2 !=null){
			if(fichero1.equals(fichero2)){ //Si el contenido del fichero1 coincide con el del fichero2 nos dará la siguiente información
				System.out.println("El Contenido Coincide en la Línea:" +contador1);
				contador1 +=1;
				JOptionPane.showMessageDialog(null, "El Contenido Coincide en la Línea "+contador1,
						"Proyecto Buffers",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/Ok2x24.png")); //Indica si coincide el contenido
			}else{//Si el contenido del fichero1 no coincide con el del fichero2 nos devolverá la siguiente información
				System.out.println("El Contenido No Coincide en la Línea: "+contador1);
				contador1 +=1;
				JOptionPane.showMessageDialog(null, "El Contenido No Coincide en la Línea "+contador1,
						"Proyecto Buffers",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png")); //Indica si no coincide el contenido
			}
		break; //Cerramos el Bucle
		}
		closeFile(); //Método para cerrar los archivos
		return true;
	}
	
	//Definimos el Método para buscar Palabras
	public int buscarPalabra (String fichero1, String palabra, boolean primera_aparicion){
		//TODO: Implementa la función
		try {
			openFile();//Llamamos al Método openFile()
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		while((fichero1) != null){
			if(fichero1.contains(palabra)){//Si el fichero1 contiene la palabra indicada, nos devolverá la siguiente información
				System.out.println("Encontrada la Palabra: " +palabra);
				primera_aparicion = true;
				contador2++; //Cuenta las Filas
				JOptionPane.showMessageDialog(null, "Palabra "+palabra+" Encontrada en Línea "+contador2,
						"Proyecto Buffers",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/Ok2x24.png")); //Indica en que Línea está la Palabra y si existe
			}else{//Si el fichero2 no contiene la palabra indicada, nos devolverá lo siguiente
				System.out.println("No Encontrada la Palabra: " +palabra);
				primera_aparicion = false;
				contador2++; //Cuenta las Filas
				JOptionPane.showMessageDialog(null, "Palabra "+palabra+" No Encontrada en la Línea "+contador2,
						"Proyecto Buffers",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png"));//Indica en que Línea está la Palabra y si no existe
			}
		break;
		}
		try {
			closeFile();//Llamamos al Método closeFile()
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Archivo No Cerrado "+e.getMessage()); //Imprimimos el Error por Consola
		} 
		return 1;
	}
	
	public int copiarArchivos(){ //Definimos el método para copiar archivos
		openCopy();
		return 1;
	}
}
