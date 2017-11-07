package libro;

public class Libro {
	private int id;
	private String titulo;
	private String autor;
	private String nombre;
	private String anyo;
	private String editor;
	private int numPaginas;
	
	public Libro(){
	}
	
	//Definimos el Constructor del Objeto Libro
	public Libro(int i,String year, String tit,String aut,String nom,String edit, int numP) {
		id = i;
		anyo = year;
		titulo = tit;
		autor = aut;
		nombre = nom;
		editor = edit;
		numPaginas = numP;
	}

	//Generamos los Getters y Setters
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

	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnyo() {
		return anyo;
	}
	public void setAnyo(String anyo) {
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
	
	//Método que imprime los Datos del Objeto Libro
	public void print(){
		System.out.println("\n"+"<<<<Datos Libro>>>>"+"\n"+
					" ID: "+id+"\n"+
					" Titulo: "+" "+"("+anyo+") "+titulo+"\n"+
					" Autor "+"\n"+
					" 	Nombre:"+nombre+"\n"+
					" Editor: "+editor+"\n"+
					" Páginas: "+numPaginas);
	}
}
