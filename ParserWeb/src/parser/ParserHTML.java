package parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserHTML {

	public static void main(String[] args) {
		String web = "https://www.pccomponentes.com/"; //Definimos la dirección web
		
		Document doc = null; //Inicializamos el Document
		try {
			doc = Jsoup.connect(web).get(); //Nos conectamos al enlace url
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        String title = doc.title(); //Obtnemos el título de la web consultada
        System.out.println(title);//Imprimimos el Título
        
        Elements data = doc.getElementsByClass("GTM-productClick enlace-superpuesto"); //Indicamos la etiqueta donde están listados los productos
        for(Element elm:data){ //Loop para que recorra todos los elementos que hay dentro de la etiqueta con esa descripción
        	if(elm.tagName().equals("a")){ //Cuando la etiqueta sea igual a "a" buscará todos los elementos indicados dentro de ella
        		//Imprimimos la Descripción del producto,el precio y la marca
        		System.out.println(">>>Producto: "+elm.attr("data-name")+
        				"  >>>Precio: "+elm.attr("data-price")+"€"+
        				"  >>>Compañía: "+elm.attr("data-brand"));
        	}

        }

	}
}
