package view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;

public class LaunchView extends JFrame{
	//Variables Componentes
	private JCheckBox primera;
	private JPanel panel;
	private JLabel label_f1,label_f2,label_pal,labelID,labelTitulo,labelAutor,labelYear,labelEditor,labelPg;
	private JTextField fichero1,fichero2,palabra,textNombre,textFieldID,textFieldTitulo,textFieldAutor,textFieldYear,textFieldEditor,textFieldPg;
	private JButton comparar,buscar,SelecArchivo, copiar,btnAlmacen1,btnAlmacen2,btnRecuperar1,
	btnRecuperar2,btnRecuperarAll,btnReiniciar,btnSalir;
	private JSeparator separator1,separator2,separator3;
	private JTextArea textArea;
	private JScrollPane scroll;

	public LaunchView() {
		try {//Agregamos el LookAndFeel de Windows
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setBounds(200,200,1000,468);
		setTitle("Proyecto Buffers");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));
		panel = new JPanel();
		panel.setBackground(new Color(95, 158, 160));
		
		//Botón Comparar Contenido
		comparar = new JButton("Comparar Contenido");
		comparar.setBounds(10, 35, 150, 26);
		comparar.setFont(new Font("Tahoma", Font.BOLD, 10));
		comparar.setToolTipText("Compara el Contenido de los Ficheros");
		comparar.setPreferredSize(new Dimension(150, 26));
		
		//Botón Buscar Palabra
		buscar = new JButton("Buscar Palabra");
		buscar.setFont(new Font("Tahoma", Font.BOLD, 11));
		buscar.setBounds(10, 220, 150, 26);
		buscar.setToolTipText("Busca Palabras en texto1.txt");
		buscar.setPreferredSize(new Dimension(150, 26));
		
		//TextFiel Fichero 1
		fichero1 = new JTextField("texto1.txt",10);
		fichero1.setEditable(false);
		fichero1.setBounds(10, 97, 150, 20);
		//TextField Fichero2
		fichero2 = new JTextField("texto2.txt",10);
		fichero2.setEditable(false);
		fichero2.setBounds(10, 153, 150, 20);
		//TextField Palabra
		palabra = new JTextField("",10);
		palabra.setBounds(10, 282, 150, 20);
		//Label Fichero 1
		label_f1 = new JLabel("Fichero 1:");
		label_f1.setBounds(10, 72, 150, 14);
		//Label Fichero 2
		label_f2 = new JLabel("Fichero 2:");
		label_f2.setBounds(10, 128, 150, 14);
		//Label Palabra
		label_pal = new JLabel("Palabra:");
		label_pal.setBounds(10, 257, 150, 14);
		//CheckBox Primera Aparición
		primera = new JCheckBox("Primera aparición");
		primera.setBounds(10, 320, 150, 23);
		primera.setBackground(new Color(255, 255, 102));
		//Text Area y ScrollPane
		scroll = new JScrollPane();
		scroll.setSize(347, 307);
		scroll.setLocation(195, 36);
		textArea = new JTextArea();
		textArea.setForeground(new Color(0, 0, 128));
		textArea.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 12));
		textArea.setBounds(192, 36, 345, 307);
		textArea.setEditable(false);
		textArea.setLineWrap(true); //Añadimos Salto de Línea Automático al textArea
		scroll.setViewportView(textArea);
		
	    //Botón Seleccionar Archivos
		SelecArchivo = new JButton("");
		SelecArchivo.setBounds(571, 35, 43, 33);
        SelecArchivo.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/folder.png")));
        SelecArchivo.setToolTipText("Abrir Buscador");
        //TextField Nombre
        textNombre = new JTextField();
        textNombre.setEditable(false);
        textNombre.setBounds(624, 35, 188, 33);
        textNombre.setColumns(10);
		panel.setLayout(null);
		//Botón Copiar
		copiar = new JButton("");
        copiar.setToolTipText("Copiar Archivo");
        copiar.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/copyFolder22.png")));
        copiar.setBounds(822, 35, 43, 33);
        //Etiqueta ID
        labelID = new JLabel("ID");
        labelID.setBounds(574, 128, 83, 14);
        //TextField ID
        textFieldID = new JTextField();
        textFieldID.setBounds(571, 153, 86, 20);
        textFieldID.setColumns(10);
        //Label Título
        labelTitulo = new JLabel("T\u00EDtulo");
        labelTitulo.setBounds(693, 128, 172, 14);
        //TextField Título
        textFieldTitulo = new JTextField();
        textFieldTitulo.setBounds(693, 153, 172, 20);
        textFieldTitulo.setColumns(10);
        //Label Autor
        labelAutor = new JLabel("Autor");
        labelAutor.setBounds(571, 184, 172, 14);
        //TextField Autor
        textFieldAutor = new JTextField();
        textFieldAutor.setBounds(571, 209, 172, 20);
        textFieldAutor.setColumns(10);
        //Etiqueta Año
        labelYear = new JLabel("Publicaci\u00F3n");
        labelYear.setBounds(782, 184, 83, 14);
        //TextField Año
        textFieldYear = new JTextField();
        textFieldYear.setBounds(779, 209, 86, 20);
        textFieldYear.setColumns(10);
        //Label Editor
        labelEditor = new JLabel("Editor");
        labelEditor.setBounds(571, 246, 172, 14);
        //TextField Editor
        textFieldEditor = new JTextField();
        textFieldEditor.setBounds(571, 271, 172, 20);
        textFieldEditor.setColumns(10);
        //Label Páginas
        labelPg = new JLabel("P\u00E1ginas");
        labelPg.setBounds(781, 246, 84, 14);
        //TextField Páginas
        textFieldPg = new JTextField();
        textFieldPg.setBounds(779, 271, 86, 20);
        textFieldPg.setColumns(10);
        //Botón Almacén 1
        btnAlmacen1 = new JButton("");
        btnAlmacen1.setToolTipText("Almac\u00E9n 1");
        btnAlmacen1.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/guardar1x24.png")));
        btnAlmacen1.setBounds(571, 310, 68, 33);
        //Botón Almacén 2
        btnAlmacen2 = new JButton("");
	    btnAlmacen2.setToolTipText("Almac\u00E9n 2");
	    btnAlmacen2.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/guardar2x24.png")));
	    btnAlmacen2.setBounds(571, 354, 68, 33);
        //Botón Recuperar Datos Almacén 1
        btnRecuperar1 = new JButton("");
        btnRecuperar1.setToolTipText("Recuperar Datos Almac\u00E9n 1");
        btnRecuperar1.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/bookx24.png")));
        btnRecuperar1.setBounds(685, 310, 68, 33);
        //Botón Recuperar Datos Almacén 2
        btnRecuperar2 = new JButton("");
	    btnRecuperar2.setToolTipText("Recuperar Datos Almac\u00E9n 2");
	    btnRecuperar2.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/book2x24.png")));
	    btnRecuperar2.setBounds(685, 354, 68, 33);
        //Botón Recuperar Todos los Datos de los Almacenes
        btnRecuperarAll = new JButton("");
        btnRecuperarAll.setToolTipText("Recuperar Todos los Datos");
        btnRecuperarAll.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/book-shelfx32.png")));
        btnRecuperarAll.setBounds(797, 325, 68, 41);
        //Botón Reiniciar
        btnReiniciar = new JButton("");
	    btnReiniciar.setToolTipText("Reiniciar Aplicaci\u00F3n");
	    btnReiniciar.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/restart1x24.png")));
	    btnReiniciar.setBounds(917, 341, 57, 33);
        //Botón Salir
        btnSalir = new JButton("");
        btnSalir.setToolTipText("Salir Aplicaci\u00F3n");
        btnSalir.setIcon(new ImageIcon(LaunchView.class.getResource("/imagenes/exit1x24.png")));
        btnSalir.setBounds(917, 385, 57, 33);
        //Separador 1
        separator1 = new JSeparator();
        separator1.setBackground(Color.GRAY);
        separator1.setBounds(571, 97, 294, 20);
        //Separador 2
        separator2 = new JSeparator();
        separator2.setBackground(Color.GRAY);
        separator2.setBounds(10, 196, 150, 13);
        //Separador 3
        separator3 = new JSeparator();
        separator3.setBackground(Color.GRAY);
        separator3.setOrientation(SwingConstants.VERTICAL);
        separator3.setBounds(889, 28, 18, 390);
		//Añadimos todos los componentes al Panel
		panel.add(comparar);
		panel.add(buscar);
		panel.add(label_f1);
		panel.add(fichero1);
		panel.add(label_f2);
		panel.add(fichero2);
		panel.add(label_pal);
		panel.add(palabra);
		panel.add(primera);
		panel.add(scroll);
		panel.add(SelecArchivo);
		panel.add(textNombre);
		panel.add(copiar);
		panel.add(labelID);
		panel.add(textFieldID);
		panel.add(labelTitulo);
		panel.add(textFieldTitulo);
		panel.add(labelAutor);
		panel.add(textFieldAutor);
		panel.add(labelYear);
	    panel.add(textFieldYear);
	    panel.add(labelEditor);
	    panel.add(textFieldEditor);
	    panel.add(labelPg);
	    panel.add(textFieldPg);
	    panel.add(btnAlmacen1);
	    panel.add(btnAlmacen2);
	    panel.add(btnRecuperar1);
	    panel.add(btnRecuperar2);
	    panel.add(btnRecuperarAll);
	    panel.add(btnReiniciar);
	    panel.add(btnSalir);
	    panel.add(separator1);
	    panel.add(separator2);
	    panel.add(separator3);
        // Añadimos el JPanel al JFrame
        this.getContentPane().add(panel);     
	}	
	
	//Generamos los Getters and Setters de los Componentes
	public JButton getComparar() {
		return comparar;
	}
	public void setComparar(JButton comparar) {
		this.comparar = comparar;
	}

	public JButton getBuscar() {
		return buscar;
	}
	public void setBuscar(JButton buscar) {
		this.buscar = buscar;
	}

	public JTextArea getTextArea() {
		return textArea;
	}
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
			
	public JTextField getFichero1() {
		return fichero1;
	}
	public void setFichero1(JTextField fichero1) {
		this.fichero1 = fichero1;
	}

	public JTextField getFichero2() {
		return fichero2;
	}
	public void setFichero2(JTextField fichero2) {
		this.fichero2 = fichero2;
	}

	public JTextField getPalabra() {
		return palabra;
	}
	public void setPalabra(JTextField palabra) {
		this.palabra = palabra;
	}
	
	public JCheckBox getPrimera() {
		return primera;
	}
	public void setPrimera(JCheckBox primera) {
		this.primera = primera;
	}
	
	//Getters and Setters Funcionalidad Copiar Archivos
	public JButton getSelecArchivo() {
		return SelecArchivo;
	}
	public void setSelecArchivo(JButton selecArchivo) {
		SelecArchivo = selecArchivo;
	}

	public JButton getCopiar() {
		return copiar;
	}
	public void setCopiar(JButton copiar) {
		this.copiar = copiar;
	}

	public JTextField getTextNombre() {
		return textNombre;
	}
	public void setTextNombre(JTextField textNombre) {
		this.textNombre = textNombre;
	}
	
	//Getters and Setters funcionalidad Almacenar Libros
	public JTextField getTextFieldID() {
		return textFieldID;
	}
	public void setTextFieldID(JTextField textFieldID) {
		this.textFieldID = textFieldID;
	}

	public JTextField getTextFieldTitulo() {
		return textFieldTitulo;
	}
	public void setTextFieldTitulo(JTextField textFieldTitulo) {
		this.textFieldTitulo = textFieldTitulo;
	}

	public JTextField getTextFieldAutor() {
		return textFieldAutor;
	}
	public void setTextFieldAutor(JTextField textFieldAutor) {
		this.textFieldAutor = textFieldAutor;
	}

	public JTextField getTextFieldYear() {
		return textFieldYear;
	}
	public void setTextFieldYear(JTextField textFieldYear) {
		this.textFieldYear = textFieldYear;
	}

	public JTextField getTextFieldEditor() {
		return textFieldEditor;
	}
	public void setTextFieldEditor(JTextField textFieldEditor) {
		this.textFieldEditor = textFieldEditor;
	}

	public JTextField getTextFieldPg() {
		return textFieldPg;
	}
	public void setTextFieldPg(JTextField textFieldPg) {
		this.textFieldPg = textFieldPg;
	}

	public JButton getBtnAlmacen1() {
		return btnAlmacen1;
	}
	public void setBtnAlmacen1(JButton btnAlmacen1) {
		this.btnAlmacen1 = btnAlmacen1;
	}

	public JButton getBtnAlmacen2() {
		return btnAlmacen2;
	}
	public void setBtnAlmacen2(JButton btnAlmacen2) {
		this.btnAlmacen2 = btnAlmacen2;
	}

	public JButton getBtnRecuperar1() {
		return btnRecuperar1;
	}
	public void setBtnRecuperar1(JButton btnRecuperar1) {
		this.btnRecuperar1 = btnRecuperar1;
	}
	
	public JButton getBtnRecuperar2() {
		return btnRecuperar2;
	}
	public void setBtnRecuperar2(JButton btnRecuperar2) {
		this.btnRecuperar2 = btnRecuperar2;
	}

	public JButton getBtnRecuperarAll() {
		return btnRecuperarAll;
	}
	public void setBtnRecuperarAll(JButton btnRecuperarAll) {
		this.btnRecuperarAll = btnRecuperarAll;
	}

	//Getters Setters Botón Reiniciar
	public JButton getBtnReiniciar() {
		return btnReiniciar;
	}
	public void setBtnReiniciar(JButton btnReiniciar) {
		this.btnReiniciar = btnReiniciar;
	}

	//Getters Setters Botón Salir
	public JButton getBtnSalir() {
		return btnSalir;
	}
	public void setBtnSalir(JButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	//Método para Mostrar Errores
	public void showError(String m){
		JOptionPane.showMessageDialog(this.panel,m,"Error",JOptionPane.ERROR_MESSAGE);
		
		if(fichero1.getText().isEmpty()){
			JOptionPane.showMessageDialog(this.panel, "El Fichero no se Encuentra o No se ha Podido Ejecutar");
		}
	}
}
