package Marshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class Marshaller {
	private Document dom = null;
	private ArrayList<Libro> libros = null;
	
	public Marshaller(ArrayList<Libro> books) {
		libros = books;
	}
	
	public void crearDocumento() { //Método para crear un Documento
		//Generamos DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//Definimos DocumentBuilder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//Generamos una Instancia DOM
			dom = db.newDocument();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}

	}
	
	public void crearArbolDOM() {
		//Creamos el Elemento Root
		Element docEle = dom.createElement("libros");
		dom.appendChild(docEle);

		@SuppressWarnings("rawtypes")
		Iterator it = libros.iterator(); //Generamos el Iterator para recorrer el documento
		while (it.hasNext()) {
			Libro lib = (Libro) it.next();
			//Para cada objeto libros se creará un elemento <libro> y se añadirá al elemento raíz
			Element personaEle = setLibro(lib);
			docEle.appendChild(personaEle);
			}
	}
	
	private Element setLibro(Libro libro1){
		//Creamos los elementos del documento
		Element LibroElem = dom.createElement("libro");

		//Creamos el elemento ID y el Nodo int de ID
		Element numId = dom.createElement("id");		
		Text id = dom.createTextNode(Integer.toString(libro1.getId()));
		numId.appendChild(id);
		LibroElem.appendChild(numId);

		//Creamos el elemento Título y el Nodo String del Título
		Element nomTitulo = dom.createElement("titulo");
		Text titulo = dom.createTextNode(libro1.getTitulo());
		nomTitulo.appendChild(titulo);
		LibroElem.appendChild(nomTitulo);
		
		//Creamos el Elemento Autor y el Nodo String de Autor
		Element nomAutor = dom.createElement("autor");
		Text autor = dom.createTextNode(libro1.getAutor());
		nomAutor.appendChild(autor);
		LibroElem.appendChild(nomAutor);
		
		//Creamos el Elemento Año y el Nodo int de Año
		Element numYear = dom.createElement("anyo");		
		Text year = dom.createTextNode(Integer.toString(libro1.getAnyo()));
		numYear.appendChild(year);
		LibroElem.appendChild(numYear);
		
		//Creamos el Elemento Editor con el Nodo String de Editor
		Element nomEditor = dom.createElement("editor");
		Text editor = dom.createTextNode(libro1.getEditor());
		nomEditor.appendChild(editor);
		LibroElem.appendChild(nomEditor);
		
		//Creamos el Elemento Páginas y el Nodo int de las Páginas
		Element numPg = dom.createElement("paginas");		
		Text pgs = dom.createTextNode(Integer.toString(libro1.getNumPaginas()));
		numPg.appendChild(pgs);
		LibroElem.appendChild(numPg);
		
		return LibroElem;
	}
	
	public void escribirDocumentAXml(File file) throws TransformerException {
		//Instacia para escribir el resultado
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		//Especificamos dónde escribimos y la fuente de datos
		StreamResult result = new StreamResult(file);
		DOMSource source = new DOMSource(dom);
		trans.transform(source, result);
	}
}
