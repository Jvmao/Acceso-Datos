package principal;

import parserLibro.Parser;

public class Principal {

	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.parseFicheroXml("biblioteca.xml");//Nombre del Fichero a Parsear
		parser.parseDocument();//Parseamos el Documento
		parser.print();//Imprimimos el Documento
	}

}
