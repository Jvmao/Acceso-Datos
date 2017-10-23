package principal;

import javax.swing.JFrame;

import controller.GestionEventos;
import model.GestionDatos;
import view.LaunchView;

public class Principal {

	public static void main(String[] args) {
		GestionDatos model = new GestionDatos();
		
		LaunchView view = new LaunchView();
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
		
		GestionEventos controller = new GestionEventos(model,view);
		controller.contol();

	}

}
