package parserLibro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import libro.Libro;

public class Parser {
	// Creamos el Objeto
	private Document dom = null;
	private ArrayList<Libro> biblioteca = null;

	public Parser() {
		biblioteca = new ArrayList<Libro>();
	}

	public void parseFicheroXml(String fichero) { // Parseamos el Fichero
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// Definimos un documentbuilder
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(fichero); // Parseamos el documento xml para obtener una representación DOM
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			System.err.println(pce);
		} catch (SAXException se) {
			se.printStackTrace();
			System.err.println(se);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.err.println(ioe);
		}
	}

	public void parseDocument() { // Parsea el Documento, recorre la lista de nodos del archivo y los va guardando
		// Definimos el Elemento Raíz
		Element docEle = dom.getDocumentElement();
		// Definimos la lista de elementos
		NodeList nl = docEle.getElementsByTagName("libro");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				// Obtenemos un elemento de Libro
				Element el = (Element) nl.item(i);
				//System.out.println(nl.item(i).getTextContent());
				// Obtenemos un Libro desde el método getLibro
				Libro p = getLibro(el);
				// Añadimos el Libro al Array
				biblioteca.add(p);
			}
		}
	}

	// Definimos los Métodos para Cada Elemento de la Clase Libro
	private String getTextValue(Element elm,String tagName) { // Recupera el Contenido de un String
		String textValor = null;
		String atr = null;
		NodeList nl = elm.getElementsByTagName(tagName); //Devuelve todos los valores dentro de la etiqueta
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textValor = el.getFirstChild().getNodeValue();
			atr = el.getAttribute("anyo"); //Devuelve el valor del atributo anyo
			System.out.println(tagName+": "+atr+" "+nl.item(0).getTextContent());
		}
		return textValor;
	}
	

	private int getIntValue(Element elm, String tagName) { // Recupera el Contenido de un integer
		return Integer.parseInt(getTextValue(elm, tagName));
	}

	private Libro getLibro(Element libroElement) {
		// Definimos los elementos que integran la clase Libro a partir de los métodos getTextValue, getIntValue
		int id = getIntValue(libroElement, "id");
		String titulo = getTextValue(libroElement,"titulo");
		String anyo = getTextValue(libroElement, "anyo");
		String autor = getTextValue(libroElement, "autor");
		String nombre = getTextValue(libroElement,"nombre");
		String editor = getTextValue(libroElement, "editor");
		int paginas = getIntValue(libroElement, "paginas");
		
		// Creamos un Nuevo Libro con sus datos correspondientes
		Libro book = new Libro(id, anyo, titulo,autor,nombre,editor, paginas);
		return book;
	}

	public void print() { // Método para imprimir el resultado por consola
		Iterator it = biblioteca.iterator();
		while (it.hasNext()) {
			Libro bk = (Libro) it.next();
			bk.print();
		}
	}
}
