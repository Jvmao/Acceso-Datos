package gestionFicherosApp;

import gestionficheros.MainGUI;

public class GestionFicherosApp {

	public static void main(String[] args) {
		//Creamos una instancia
		GestionFicherosImpl getFicherosImpl = new GestionFicherosImpl();
		//Creamos una nueva Interfaz Gr�fica
		new MainGUI(getFicherosImpl).setVisible(true);
	}

}
