package controller;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import libro.Libro;

public class Almacen {
	public void Almacen(){	//Constructor Vacío
	}
	
	//Definimos el Método para Guardar el Objeto Libro
	public int guardar(Libro libro,String l) throws IOException{
		ObjectOutputStream out=null;
		
		try {
			out = new ObjectOutputStream(new FileOutputStream(l));
			out.writeObject(libro);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			intentarCerrar(out);
		}
		return 1;
	}
	
	//Definimos el Método para Cerrar el objeto Libro
	public void intentarCerrar(Closeable c) throws IOException{
		if(c !=null){
			c.close();
		}
	}
	
	//Definimos el Método para recuperar el Objeto Libro
	public Libro recuperarLibro(String l) throws IOException{
		Libro libro = null;
		ObjectInputStream in = null;
		 try {
			in = new ObjectInputStream(new FileInputStream(l));
			libro = (Libro) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			intentarCerrar(in);
		}
		return libro;
	}
	
	/*public ArrayList<Libro>recuperarTodos(){
		ArrayList<Libro> libros=new ArrayList<Libro>();
		Libro l = new Libro();

		libros.add(l);
		libros.add(new Libro());
		
		Iterator it = libros.iterator();

		while(it.hasNext()){
			Libro l2 = (Libro) it.next();
			l2.print();
		}
		return libros;
			
	}*/
}
