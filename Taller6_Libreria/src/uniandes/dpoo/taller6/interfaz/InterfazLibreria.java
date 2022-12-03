package uniandes.dpoo.taller6.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import uniandes.dpoo.taller6.modelo.Categoria;
import uniandes.dpoo.taller6.modelo.Libreria;
import uniandes.dpoo.taller6.modelo.Libro;

/**
 * Esta clase representa a la ventana principal de la aplicaciÃ³n
 */
@SuppressWarnings("serial")
public class InterfazLibreria extends JFrame {

	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * Esta es la librerÃ­a que se muestra durante la ejecuciÃ³n de la apl
	 * caciÃ³n
	 */
	private Libreria libreria;

	// ************************************************************************
	// Elementos de la interfaz
	// ************************************************************************

	/**
	 * Este componente corresponde al menÃº completo que se encuentra en la parte
	 * superior de la ventana
	 */
	private JMenuBar barraMenu;

	/**
	 * Este componente corresponde al menÃº archivo
	 */
	private JMenu menuArchivo;

	/**
	 * Este componente corresponde a la opciÃ³n para cargar los archivos de una
	 * librerÃ­a
	 */
	private JMenuItem menuAbrir;

	/**
	 * Este componente corresponde a la opciÃ³n para salir de la aplicaciÃ³n
	 */
	private JMenuItem menuSalir;

	/**
	 * Este componente corresponde al panel donde se muestran las categorÃ­as
	 * disponibles en la aplicaciÃ³n
	 */
	private PanelCategorias panelCategorias;

	/**
	 * Este componente corresponde al panel donde se muestra una lista de libros
	 */
	private PanelLibros panelLibros;

	/**
	 * Este componente corresponde al panel donde se muestra la informaciÃ³n de un
	 * libro
	 */
	private PanelLibro panelLibro;

	/**
	 * Este componente corresponde al panel con los botones de la parte inferior de
	 * la ventana
	 */
	private PanelBotones panelBotones;

	// ************************************************************************
	// Constructores
	// ************************************************************************

	/**
	 * Construye la ventana principal para la aplicaciÃ³n, pero no carga la
	 * informaciÃ³n de ninguna librerÃ­a.
	 */
	public InterfazLibreria() {
		barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);

		menuArchivo = new JMenu("Archivo");
		barraMenu.add(menuArchivo);

		// Setting the accelerator:
		menuAbrir = new JMenuItem("Abrir", KeyEvent.VK_A);
		menuAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menuAbrir.setActionCommand(ListenerMenu.ABRIR_LIBROS);
		menuAbrir.addActionListener(new ListenerMenu(this));
		menuArchivo.add(menuAbrir);

		menuSalir = new JMenuItem("Salir", KeyEvent.VK_Q);
		menuSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuArchivo.add(menuSalir);

		JPanel panelArriba = new JPanel(new GridLayout(1, 2));
		add(panelArriba, BorderLayout.CENTER);

		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelArriba.add(panelIzquierdo);

		panelCategorias = new PanelCategorias(this);
		panelIzquierdo.add(panelCategorias, BorderLayout.NORTH);

		panelLibros = new PanelLibros(this);
		panelIzquierdo.add(panelLibros, BorderLayout.CENTER);

		JPanel panelDerecha = new JPanel(new BorderLayout());
		panelArriba.add(panelDerecha);
		panelLibro = new PanelLibro();
		panelDerecha.add(panelLibro, BorderLayout.CENTER);

		JPanel panelAbajo = new JPanel(new BorderLayout());
		panelBotones = new PanelBotones(this);
		panelAbajo.add(panelBotones, BorderLayout.CENTER);
		add(panelAbajo, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("LibrerÃ­a");
		setSize(1200, 700);
		setVisible(true);

		// Cargar archivos
		String path = Path.of("").toAbsolutePath().toString() + "/Taller6_Libreria/data/";
		File archivoCategorias = new File(path + "categorias.csv");
		File archivoLibros = new File(path + "libreria.csv");

		cargarArchivos(archivoCategorias, archivoLibros);
		mostrarCategoriasFaltantes();
	}

	// ************************************************************************
	// MÃ©todos
	// ************************************************************************

	/**
	 * Carga la informaciÃ³n de una librerÃ­a a partir de los archivos datos,
	 * construye un objeto LibrerÃ­a con esa informaciÃ³n y lo deja en el atr
	 * buto
	 * llamado 'libreria'
	 * 
	 * @param archivo_categorias El archivo que tiene la informaciÃ³n de las
	 *                           categorÃ­as que se usarÃ¡n para los libros
	 * @param archivo_libros     El archivo que tiene la informaciÃ³n de los l
	 *                           bros
	 */
	public void cargarArchivos(File archivo_categorias, File archivo_libros) {
		try {
			libreria = new Libreria(archivo_categorias.getPath(), archivo_libros.getPath());
			panelCategorias.actualizarCategorias(libreria.darCategorias());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Hubo un error leyendo los archivos", "Error de lectura",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	/*
	 * Cambiar Categoria
	 */
	public void renombrarCategoria() {

		try {
			String viejo = JOptionPane.showInputDialog(this, "Escriba el nombre de la categoria a renombrar",
					"Categoria a renombrar");
			String nuevo = JOptionPane.showInputDialog(this, "Escriba el nombre nuevo", "Renombrar");
			libreria.renombrarCategoria(viejo, nuevo);
			JOptionPane.showMessageDialog(this, "Se renombro correctamente la categoria", "Renombrar Exitoso",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "No se pudo renombrar la categoria", "No se pudo renombrar",
					JOptionPane.WARNING_MESSAGE);

		}

	}

	// metodo borrar libros

	public void borrarLibros(String autoresString) {

		if (autoresString.equals("Autores")) {

			JOptionPane.showMessageDialog(this, "Digite Autor(es) a eliminar", "Error",
					JOptionPane.ERROR_MESSAGE);

		} else {

			try {
				libreria.borrarLibros(autoresString);

				JOptionPane.showMessageDialog(this,
						"Se eliminaron los libros de " + autoresString + "\nActualice la categoria seleccionada",
						"Borrar Libros Exitoso",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	/*
	 * Metodo Categorias faltantes
	 */
	public void mostrarCategoriasFaltantes() {
		ArrayList<Categoria> categorias = libreria.getCreadasObjeto();

		if (categorias.size() > 0) {

			String anuncio = "";

			for (int i = 0; i < categorias.size(); i++) {

				anuncio += categorias.get(i).darNombre() + ": ";
				anuncio += categorias.get(i).contarLibrosEnCategoria() + ", ";
			}

			JOptionPane.showMessageDialog(this, anuncio, "Categorias Nuevas Creadas",
					JOptionPane.INFORMATION_MESSAGE);

		}
	}

	/**
	 * Cambia la categoría para la cual se deben mostrar los libros en el panel
	 * panelLibros
	 * 
	 * @param categoria La categoría para la que se deben mostrar los libros ahora
	 */
	public void cambiarCategoria(Categoria categoria) {
		ArrayList<Libro> libros = libreria.darLibros(categoria.darNombre());
		panelLibros.actualizarLibros(libros);
		mostrarLibro(libros.get(0));
	}

	/**
	 * Cambia el libro para el cual se debe mostrar la información en el panel
	 * panelLibro
	 * 
	 * @param libro El libro para el que se debe mostrar la información
	 */
	public void mostrarLibro(Libro libro) {
		panelLibro.actualizarLibro(libro);
	}

	/**
	 * Le pide al usuario el título de un libro y lo busca en la librería.
	 * 
	 * Si existe un libro, le muestra al usuario la información del libro en el
	 * panel 'panelLibro'.
	 */
	public void buscarLibro() {
		String titulo = JOptionPane.showInputDialog(this, "Escriba el título del libro que busca", "titulo");
		if (titulo != null) {
			Libro libro = libreria.buscarLibro(titulo);
			if (libro == null) {
				JOptionPane.showMessageDialog(this, "No se encontró un libro con ese título", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				mostrarLibro(libro);
			}
		}
	}

	/**
	 * Busca los libros de un autor a partir de una parte del nombre del autor que
	 * haya dado el usuario.
	 * 
	 * La lista de libros que correspondan al autor dado se muestra en el panel
	 * panelLibros.
	 */
	public void buscarLibrosAutor() {
		String autor = JOptionPane.showInputDialog(this, "Escriba al menos una parte del autor que busca", "autor");
		if (autor != null) {
			ArrayList<Libro> libros = libreria.buscarLibrosAutor(autor);
			if (libros.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay ningún autor con ese nombre", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				panelLibros.actualizarLibros(libros);
				mostrarLibro(libros.get(0));
			}
		}
	}

	/**
	 * Le pide al usuario el nombre de un autor y le informa en qué categorías hay
	 * libros de ese autor.
	 */
	public void buscarCategoriasAutor() {

		String autor = JOptionPane.showInputDialog(this, "Escriba el nombre del autor que está buscando", "autor");
		if (autor != null) {
			ArrayList<Categoria> categorias = libreria.buscarCategoriasAutor(autor);
			if (categorias.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay ningún autor con ese nombre", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				String mensaje = "Hay libros de ese autor en las siguientes categorías:\n";
				for (Categoria categoria : categorias) {
					mensaje += " " + categoria.darNombre() + "\n";
				}
				JOptionPane.showMessageDialog(this, mensaje, "Categorías", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Le informa al usuario la calificación promedio de los libros de la
	 * ibrería,
	 * con base en la información disponible en cada uno de los libros.
	 */
	public void calcularCalificacionPromedio() {
		double calificacion = libreria.calificacionPromedio();
		calificacion = (double) ((int) calificacion * 1000) / 1000;
		JOptionPane.showMessageDialog(this, "La calificación promedio de los libros es " + calificacion,
				"Calificación promedio", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario cuál es la categoría con más libros en la librería.
	 */
	public void categoriaConMasLibros() {
		Categoria cat = libreria.categoriaConMasLibros();
		int cantidad = cat.contarLibrosEnCategoria();
		String mensaje = "La categoría con más libros es " + cat.darNombre() + " y tiene " + cantidad + " libros";
		JOptionPane.showMessageDialog(this, mensaje, "Categoría con más libros", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario la cantidad de libros en la librería para los cuales
	 * o
	 * se tiene una portada.
	 */
	public void contarSinPortada() {
		int cantidad = libreria.contarLibrosSinPortada();
		String mensaje = "Hay " + cantidad + " libros sin portada";
		JOptionPane.showMessageDialog(this, mensaje, "Libros sin portada", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario cuál es la categoría cuyos libros están mejor
	 * calificados.
	 */
	public void categoriaMejorCalificacion() {
		Categoria cat = libreria.categoriaConMejoresLibros();
		double calificacion = cat.calificacionPromedio();
		calificacion = (double) ((int) calificacion * 1000) / 1000;
		String mensaje = "La categoría con la mejor calificación es " + cat.darNombre()
				+ ".\nLa calificación promedio de los libros es " + calificacion;
		JOptionPane.showMessageDialog(this, mensaje, "Categoría con mejor calificación promedio",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario si hay un autor que tenga libros en más de una
	 * categoría.
	 */
	public void hayAutorEnVariasCategorias() {
		boolean hay = libreria.hayAutorEnVariasCategorias();
		String mensaje = "No hay ningún autor con al menos un libro en dos categorías diferentes.";
		if (hay) {
			mensaje = "Hay al menos un autor con al menos un libro en dos categorías diferentes.";
		}
		JOptionPane.showMessageDialog(this, mensaje, "Consulta", JOptionPane.INFORMATION_MESSAGE);
	}

	// ************************************************************************
	// Main
	// ************************************************************************

	/**
	 * Método inicial de la aplicación
	 * 
	 * @param args Parámetros introducidos por el usuario en la línea de comandos
	 * @throws IOException
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		/*
		 * for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		 * {
		 * if ("Nimbus".equals(info.getName()))
		 * {
		 * UIManager.setLookAndFeel(info.getClassName());
		 * break;
		 * }
		 * }
		 */
		FlatLightLaf.install();
		// UIManager.setLookAndFeel( new FlatDarculaLaf());

		new InterfazLibreria();
	}

}
