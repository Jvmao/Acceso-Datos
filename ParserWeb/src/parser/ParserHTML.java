package parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserHTML {

	public static void main(String[] args) {
		String web = "https://www.pccomponentes.com/"; //Definimos la direcci�n web
		
		Document doc = null; //Inicializamos el Document
		try {
			doc = Jsoup.connect(web).get(); //Nos conectamos al enlace url
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        String title = doc.title(); //Obtnemos el t�tulo de la web consultada
        System.out.println(title);//Imprimimos el T�tulo
        
        Elements data = doc.getElementsByClass("GTM-productClick enlace-superpuesto"); //Indicamos la etiqueta donde est�n listados los productos
        for(Element elm:data){ //Loop para que recorra todos los elementos que hay dentro de la etiqueta con esa descripci�n
        	if(elm.tagName().equals("a")){ //Cuando la etiqueta sea igual a "a" buscar� todos los elementos indicados dentro de ella
        		//Imprimimos la Descripci�n del producto,el precio y la marca
        		System.out.println(">>>Producto: "+elm.attr("data-name")+
        				"  >>>Precio: "+elm.attr("data-price")+"�"+
        				"  >>>Compa��a: "+elm.attr("data-brand"));
        	}

        }

	}
}
