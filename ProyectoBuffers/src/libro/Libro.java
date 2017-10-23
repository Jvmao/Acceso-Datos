package libro;

import java.io.Serializable;

public class Libro implements Serializable {
	private int id;
	private String titulo;
	private String autor;
	private int publicacion;
	private String editor;
	private int numPaginas;
	
	public Libro(){
	}
	
	//Definimos el Constructor del Objeto Libro
	public Libro(int i, String tit, String aut, int publi, String edit, int numP) {
		id = i;
		titulo = tit;
		autor = aut;
		publicacion = publi;
		editor = edit;
		numPaginas = numP;
	}

	//Generamos los Getters and Setters del Objeto Libro
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

	public int getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(int publicacion) {
		this.publicacion = publicacion;
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
	
	//Método que imprime los Datos del Objeto Libro
	public void print(){
		System.out.println("ID: "+id+" Titulo: "+titulo+" Autor: "+autor+
				" Publicación: "+publicacion+" Editor: "+editor+
				" Páginas: "+numPaginas);
	}
	
	//Método toString del Objeto Libro
	@Override
	public String toString() {
		return "ID= " + id +"\n"+ "Título= " + titulo +"\n"+ "Autor= " + autor +"\n"+ "Año Publicacion= " + publicacion
				+"\n"+ "Editor= " + editor +"\n"+ "Número Páginas= " + numPaginas+"\n";
	}
	
	
}
