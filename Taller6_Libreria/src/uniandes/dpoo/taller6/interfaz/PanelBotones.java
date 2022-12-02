package uniandes.dpoo.taller6.interfaz;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * En este panel se tienen los botones con acciones para realizar sobre la
 * librería.
 * 
 * Este panel también cumple el rol de ser el Listeners para sus propios
 * botones.
 */
@SuppressWarnings("serial")
public class PanelBotones extends JPanel implements ActionListener {
	// ************************************************************************
	// Constantes
	// ************************************************************************

	private final static String BUSCAR_LIBRO = "BuscarLibro";

	private final static String BUSCAR_LIBROS_AUTOR = "BuscarLibrosAutor";

	private final static String BUSCAR_CATEGORIA_AUTOR = "BuscarCategoriaAutor";

	private final static String CALIFICACION = "CalificacionPromedio";

	private final static String CATEGORIA_MAS_LIBROS = "CategoriaMasLibros";

	private final static String CATEGORIA_MEJOR = "MejorCategoria";

	private final static String CONTAR_SIN_PORTADA = "ContarSinPortada";

	private final static String AUTOR_VARIAS_CATEGORIAS = "HayAutorEnVariasCategorias";

	// Cambiar nombre
	private final static String CAMBIAR_NOMBRE_CATEGORIA = "CambiarNombreCategoria";

	private final static String BORRAR_LIBRO = "BorrarLibros";

	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * Ventana que contiene al panel
	 */
	private InterfazLibreria ventana;

	// ************************************************************************
	// Constructores
	// ************************************************************************

	/**
	 * Construye un nuevo panel para mostrar los botones con acciones
	 * 
	 * @param interfazLibreria La ventana dentro de la que se encuentra el panel
	 */
	public PanelBotones(InterfazLibreria interfazLibreria) {
		ventana = interfazLibreria;
		setBorder(new TitledBorder("Acciones"));

		setLayout(new FlowLayout());

		String path = Path.of("").toAbsolutePath().toString() + "/Taller6_Libreria/data/iconos/";

		agregarBoton(BUSCAR_LIBRO, "Buscar libro con titulo", path + "libros.png");
		agregarBoton(BUSCAR_LIBROS_AUTOR, "Buscar libros de un autor", path + "autor2.png");
		agregarBoton(BUSCAR_CATEGORIA_AUTOR, "Buscar categorías autor", path + "contenido.png");
		agregarBoton(CALIFICACION, "Calificacion", path + "estrella.png");
		agregarBoton(CATEGORIA_MAS_LIBROS, "Categoria mas libros", path + "libreria.png");
		agregarBoton(CONTAR_SIN_PORTADA, "Contar sin portada", path + "libro.png");
		agregarBoton(CATEGORIA_MEJOR, "Categoria mejor", path + "premio.png");
		agregarBoton(AUTOR_VARIAS_CATEGORIAS, "Autor varias categorias", path + "autor.png");

		// Cambiar nombre a categoria
		JButton botonCambiarNombreCategoria = new JButton("Cambiar Nombre Categoria");
		botonCambiarNombreCategoria.setToolTipText("Cambiar el nombre de la categoría");
		botonCambiarNombreCategoria.setActionCommand(CAMBIAR_NOMBRE_CATEGORIA);
		botonCambiarNombreCategoria.addActionListener(this);
		this.add(botonCambiarNombreCategoria);

		//borrar libros
		JButton botonBorrarLibro = new JButton("Borrar libro");
		botonBorrarLibro.setToolTipText("Eliminar libros");
		botonBorrarLibro.setActionCommand(BORRAR_LIBRO);
		botonBorrarLibro.addActionListener(this);
		this.add(botonBorrarLibro);
	}

	// ************************************************************************
	// Métodos
	// ************************************************************************

	/**
	 * Este método sirve para agregar un botón al panel con las características
	 * especificadas en los atributos. Este método sólo debería usarse durante la
	 * construcción del panel.
	 * 
	 * @param comando El comando asociado al botón que se usará para identificarlo
	 *                cuando se haga click.
	 * @param texto   El texto que se mostrará en el "tooltip" (ayuda) del botón.
	 * @param imagen  La ruta a la imagen que se usará como ícono del botón.
	 */
	private void agregarBoton(String comando, String texto, String imagen) {

		JButton boton = new JButton();

		boton.setActionCommand(comando);
		boton.setToolTipText(texto);
		boton.setIcon(new ImageIcon(imagen));
		boton.addActionListener(this);
		this.add(boton);

	}

	// ************************************************************************
	// Métodos implementados de la interfaz ActionListener
	// ************************************************************************

	/**
	 * Este es el método que se invoca cuando se hace click sobre alguno de los
	 * botones en el panel.
	 * 
	 * Invoca un método diferente de la ventana principal de la aplicación
	 * dependiendo del botón que haya sido presionado.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		System.out.println(comando);

		if (BUSCAR_LIBRO.equals(comando)) {
			ventana.buscarLibro();
		} else if (BUSCAR_LIBROS_AUTOR.equals(comando)) {
			ventana.buscarLibrosAutor();
		} else if (BUSCAR_CATEGORIA_AUTOR.equals(comando)) {
			ventana.buscarCategoriasAutor();
		} else if (CALIFICACION.equals(comando)) {
			ventana.calcularCalificacionPromedio();
		} else if (CATEGORIA_MAS_LIBROS.equals(comando)) {
			ventana.categoriaConMasLibros();
		} else if (CONTAR_SIN_PORTADA.equals(comando)) {
			ventana.contarSinPortada();
		} else if (CATEGORIA_MEJOR.equals(comando)) {
			ventana.categoriaMejorCalificacion();
		} else if (AUTOR_VARIAS_CATEGORIAS.equals(comando)) {
			ventana.hayAutorEnVariasCategorias();

		} else if (CAMBIAR_NOMBRE_CATEGORIA.equals(comando)) {

			ventana.renombrarCategoria();

		} else if (BORRAR_LIBRO.equals(comando)){

			ventana.borrarLibro();
		}
	}

}
