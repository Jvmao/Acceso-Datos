package Marshaller;

import java.io.Serializable;

public class Libro implements Serializable{
	private int id = 0;
	private String titulo = null;
	private String autor = null;
	private int anyo = 0;
	private String editor = null;
	private int numPaginas = 0;
	
	public Libro(){
	}
	
	//Definimos el Constructor del Objeto Libro
	public Libro(int i,String tit,String aut,int year,String edit, int numP) {
		id = i;
		titulo = tit;
		autor = aut;
		anyo = year;
		editor = edit;
		numPaginas = numP;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getAnyo() {
		return anyo;
	}
	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}
	
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	public int getNumPaginas() {
		return numPaginas;
	}
	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}
	
	
}
