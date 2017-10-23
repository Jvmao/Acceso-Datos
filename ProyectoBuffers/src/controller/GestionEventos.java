package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import libro.Libro;
import model.GestionDatos;
import view.LaunchView;

public class GestionEventos {
	//Declaramos las Variables 
	private GestionDatos model;
	private LaunchView view;
	private ActionListener actionListener_comparar, actionListener_buscar, actionListener_abrir,actionListener_copiar,
	actionListener_libro1,actionListener_recuperarLibro1,actionListener_libro2,actionListener_recuperarLibro2,
	actionListener_recuperarTodo,actionListener_Reiniciar,actionListener_Salir;
	private JPanel contentPane;
	
	private String fichero1 = "texto1.txt"; //Definimos la Variable String del fichero 1 para definir el contenido a leer
	private String fichero2 = "texto2.txt"; //Definimos la Variable String del fichero 2 para definir el contenido a leer
	
	private JFileChooser jf = new JFileChooser(); //Variable para seleccionar archivos desde directorio
	
	private FileInputStream fi; //Inicializamos FileInputStream
	private FileOutputStream fo; //Inicializamos FileOutputStream
	private DataOutputStream size; //Inicializamos DataOutputStream
	private byte[] bytes =new byte[20000]; //Tamañp en bytes del nuevo fichero
	
	private Libro libro1 = new Libro(); //Generamos un nuevo objeto libro para el Libro 1
	private Libro libro2 = new Libro(); //Generamos un nuevo objeto libro para el Libro 2
	private Libro libro3 = new Libro(); //Generamos un nuevo objeto libro para el Libro 3
	private Almacen almacen = new Almacen();//Generamos un nuevo objeto almacen para almacenar los datos
	private ArrayList<Libro> librosRecuperados = new ArrayList<Libro>(); //Creamos el ArrayList para recuperar todos los datos
	
	//Método para Gestionar los Eventos
	public GestionEventos(GestionDatos model, LaunchView view) {
		this.model = model;
		this.view = view;
	}

	public void contol() { //Método para controlar los Action Listener
		actionListener_comparar = new ActionListener() {//ActionListener para comparar el Contenido
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la función call_compararContenido
				try {
					call_compararContenido(); //Llamamos al Método compararContenido
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.err.println(e); //Imprimimos el error por consola
					JOptionPane.showMessageDialog(null, "Archivo no Encontrado ",
							"ERROR!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png")); //Mensaje de Error
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println(e); //Imprimimos el error por consola
					JOptionPane.showMessageDialog(null, "FileIO Excepción ",
							"ERROR!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png")); //Mensaje de Error
				}
			}
		};
		view.getComparar().addActionListener(actionListener_comparar);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//ActionListener para Buscar Palabras
		actionListener_buscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la función call_buscarPalabra
				try {
					if(view.getPalabra().getText().equals("")){ //Mensaje si el campo de texto está vacío
						JOptionPane.showMessageDialog(null, "Campo Vacío. Introduzca una Palabra a Buscar. ",
								"ERROR!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png")); //Mensaje de Error
					}else{
						call_buscarPalabra(); //Llamamos al método buscarPalabra
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.err.println(e); //Imprimimos el error por consola
					JOptionPane.showMessageDialog(null, "Archivo no Encontrado ",
							"ERROR!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png")); //Mensaje de Error
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println(e); //Imprimimos el error por consola
					JOptionPane.showMessageDialog(null, "FileIO Excepción ",
							"ERROR!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png")); //Mensaje de Error
				}
			}
		};
		view.getBuscar().addActionListener(actionListener_buscar);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Añadimos el Método abrirArchivo para el ActionListener del Botón SelecArchivo
		actionListener_abrir = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					abrirArchivo();
				}
			};
		view.getSelecArchivo().addActionListener(actionListener_abrir);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Añadimos el Método copiarArchivo para el ActionListener del Botón Copiar
		actionListener_copiar = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
					// Pasamos los Bytes a formato decimal
					DecimalFormat decimalFormat = new DecimalFormat("#,##0");
					try{
					call_copiarArchivos(); //Introducimos Método call_copiarArchivos()
					//File copia = new File(System.getProperty("user.dir")+"\n","src/imagenes"+jf.getSelectedFile());
					File copia = new File(jf.getSelectedFile().toString());//Definimos el File para el fichero seleccionado
					File copiado = new File("src/imagenes/copia.jpg"); //Definimos el File para la ruta del nuevo archivo copiado
					//Salida de los Datos a través del textArea
					view.getTextArea().setText(">Ruta Fichero Original: "+"\n"+copia.getPath()+"\n"+
							">Tamaño Fichero Original: "+decimalFormat.format(copia.length() / 1024) + " KB"+"\n"+
							">Ruta Fichero Copiado: "+"\n"+copiado.getAbsolutePath()+"\n"+
							">Tamaño Fichero Copiado: "+decimalFormat.format(bytes.length/1024)+ " KB");
					//Mostramos mensaje de confirmación con icono personalizado
					JOptionPane.showMessageDialog(null, "Archivo Copiado con Éxito","Proyecto Buffers", 
							JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/copy.png")); //Mensaje de Confirmación
					}catch(Exception e1){
						e1.printStackTrace();
						System.err.println("Error Ejecución: "+e1.getMessage());
						JOptionPane.showMessageDialog(null, "Archivo No Econtrado ",
								"ERROR!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png")); //Mensaje de Error
					}
				}
			};
		view.getCopiar().addActionListener(actionListener_copiar);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Añadimos el ActionListener para guardar los datos del libro
		actionListener_libro1 = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				libro1.setId(Integer.parseInt(view.getTextFieldID().getText().toString())); //Obtenemos el ID del Libro
				libro1.setTitulo(view.getTextFieldTitulo().getText()); //Obtenemos el título del libro
				libro1.setAutor(view.getTextFieldAutor().getText()); //Obtenemos el Autor del libro
				libro1.setPublicacion(Integer.parseInt(view.getTextFieldYear().getText().toString())); //Obtenemos el año de Publicación
				libro1.setEditor(view.getTextFieldEditor().getText()); //Obtenemos el Editor del libro
				libro1.setNumPaginas(Integer.parseInt(view.getTextFieldPg().getText().toString())); //Obtenemos las páginas del libro
				view.getTextArea().setText("Datos Libro 1 Guardados:"+"\n"+libro1); //El textArea muestra los datos guardados
				
				try {
					almacen.guardar(libro1, "libro1.dat"); //Guardamos el libro con el nombre especificado
				} catch (IOException e1) {
					e1.printStackTrace();
					System.err.println("Error Alcenamiento Libro 1: "+e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error al Almacenar los Datos del Libro 1 ",
							"ERROR!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png"));
				}
			}
		};
		view.getBtnAlmacen1().addActionListener(actionListener_libro1);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Action Listener Para Almacenar el Libro 2
		actionListener_libro2 = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				libro2.setId(Integer.parseInt(view.getTextFieldID().getText().toString())); //Obtenemos el ID del Libro
				libro2.setTitulo(view.getTextFieldTitulo().getText()); //Obtenemos el título del libro
				libro2.setAutor(view.getTextFieldAutor().getText()); //Obtenemos el Autor del libro
				libro2.setPublicacion(Integer.parseInt(view.getTextFieldYear().getText().toString())); //Obtenemos el año de Publicación
				libro2.setEditor(view.getTextFieldEditor().getText()); //Obtenemos el Editor del libro
				libro2.setNumPaginas(Integer.parseInt(view.getTextFieldPg().getText().toString())); //Obtenemos las páginas del libro
				view.getTextArea().setText("Datos Libro 2 Guardados:"+"\n"+libro2); //El textArea muestra los datos guardados
				
				try {
					almacen.guardar(libro2, "libro2.dat"); //Guardamos el libro con el nombre especificado
				} catch (IOException e1) {
					e1.printStackTrace();
					System.err.println("Error Almacenamiento Libro 2: "+e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error al Almacenar los Datos del Libro 2 ",
							"ERROR!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/wrong1x24.png"));
				}
			}
		};
		view.getBtnAlmacen2().addActionListener(actionListener_libro2);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Añadimos el ActionListener para Recuperar los datos del libro 1
		actionListener_recuperarLibro1 = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					libro1 = almacen.recuperarLibro("libro1.dat");
					view.getTextArea().setText("Datos Recuperados del Libro 1: "+"\n"+libro1);
				} catch (IOException e1) {
					e1.printStackTrace();
					e1.getMessage();
				}
			}
		};
		view.getBtnRecuperar1().addActionListener(actionListener_recuperarLibro1);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Añadimos el ActionListener para Recuperar los datos del libro 2
		actionListener_recuperarLibro2 = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					libro2 = almacen.recuperarLibro("libro2.dat");
					view.getTextArea().setText("Datos Recuperados del Libro 2: "+"\n"+libro2);
				} catch (IOException e1) {
					e1.printStackTrace();
					e1.getMessage();
				}
			}
		};
		view.getBtnRecuperar2().addActionListener(actionListener_recuperarLibro2);
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Añadimos el ActionListener para Recuperar todos los Datos
		actionListener_recuperarTodo = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				recuperarTodos(); //Añadimos el método para recuperar todos los libros guardados
				view.getTextArea().setText("Datos Recuperados de los Libros Guardados: "+"\n"+librosRecuperados);
			}
		};
		view.getBtnRecuperarAll().addActionListener(actionListener_recuperarTodo);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Añadimos el ActionListener para Reiniciar la Aplicación
		actionListener_Reiniciar = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				view.getPalabra().setText("");
				view.getPrimera().setSelected(false);
				view.getTextNombre().setText("");
				view.getTextFieldID().setText("");
				view.getTextFieldTitulo().setText("");
				view.getTextFieldAutor().setText("");
				view.getTextFieldYear().setText("");
				view.getTextFieldEditor().setText("");
				view.getTextFieldPg().setText("");
				view.getTextArea().setText("");
			}
		};
		view.getBtnReiniciar().addActionListener(actionListener_Reiniciar);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Añadimos el ActionListener para Salir de la Aplicación
		actionListener_Salir = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		view.getBtnSalir().addActionListener(actionListener_Salir);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	
	//Método para comparar contenido
	private int call_compararContenido() throws IOException { 
		// TODO: Llamar a la función compararContenido de GestionDatos
	    BufferedReader br1 = new BufferedReader(new FileReader(fichero1)); //Definimos el BufferedReader del fichero1
	    BufferedReader br2 = new BufferedReader(new FileReader(fichero2)); //Definimos el BufferedReader del fichero2
	    String linea1; //Definimos el String linea1 para que compare el fichero linea por liena
	    String linea2; //Definimos el String linea1 para que compare el fichero linea por liena

		while((linea1 = br1.readLine()) != null && (linea2 = br2.readLine()) !=null){
			view.getTextArea().setText("Fichero 1 --- "+linea1+"\n"+"Fichero 2 --- "+linea2+"\n"); //Mostramos el Contenido del Fichero 1 y 2 por el Objeto TextArea
			model.compararContenido(linea1, linea2); //Llama al método definido en GestionDatos para comparar el contenido
		}		
		//Cerramos los Ficheros desde la clase GestionDatos
		br1.close();
		br2.close();
		return 1;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Método para buscar palabra
	private int call_buscarPalabra() throws IOException { 
		// TODO: Llamar a la función buscarPalabra de GestionDatos
		BufferedReader buf1 = new BufferedReader(new FileReader("texto1.txt")); //BufferedReader para el fichero 1
		
		//Definimos las Variables para inicializar el método buscarPalabra
		String fichero1;
		String palabra = view.getPalabra().getText().toString();
		boolean primera_aparacion = true;
		int contador =0; //Para contar el número de líneas
		
		while((fichero1 = buf1.readLine()) != null){
			if(fichero1.contains(palabra)){
				view.getTextArea().setText("Palabra Buscada >> "+palabra); //Mostramos la palabra buscada en el JTextArea
				model.buscarPalabra(fichero1, palabra, primera_aparacion); //Llamamos al método buscarPalabra desde la clase GestionDatos
				contador++;
				if(contador == 1){
					view.getPrimera().setSelected(true); //El CheckBox se Activa cuando encuentra la palabra en la primera linea
				}
			}else{
				view.getTextArea().setText("Palabra Buscada >> "+palabra); //Mostramos la palabra buscada en el JTextArea
				model.buscarPalabra(fichero1, palabra, primera_aparacion); //Llamamos al método buscarPalabra desde la clase GestionDatos
				view.getPrimera().setSelected(false);
			}
		}
		buf1.close(); //Cerramos el Buffer
		return 1;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// Añadimos el Método Abrir y Seleccionar Archivos//
	public void abrirArchivo() {// JFileChooser para seleccionar carpeta de archivos
		int s = jf.showOpenDialog(contentPane);
		File selectedFile = jf.getSelectedFile();
		if (s == JFileChooser.APPROVE_OPTION) {
			view.getTextNombre().setText(selectedFile.getName());
		} else if (s == JFileChooser.CANCEL_OPTION) {
			view.getTextNombre().setText("");
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Definimos el Método para Copiar Archivos
	public int call_copiarArchivos(){
		try {
			fi = new FileInputStream(jf.getSelectedFile());
			fo = new FileOutputStream("src/imagenes/copia.jpg");
			size = new DataOutputStream(fo);
			fi.read(bytes);
			fo.write(bytes);
			size.writeDouble(5.5);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Archivo No Encontrado"+e.getMessage());
		} catch (IOException e){
			e.printStackTrace();
			System.err.println("Excepción FileIO"+e.getMessage());
		}finally{
			try {
				fi.close();
				fo.close();
				size.close();
			} catch (IOException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
		return 1;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Método ArrayList para recuperar todos los datos almacenados
	public ArrayList<Libro>recuperarTodos(){
		librosRecuperados.add(libro1);
		librosRecuperados.add(libro2);
		
		@SuppressWarnings("rawtypes")
		Iterator it = librosRecuperados.iterator();

		while(it.hasNext()){
			libro3 = (Libro) it.next();
			libro3.print();
		}
		return librosRecuperados;	
	}
	//////////////////////////////////////////////////////////////
}
