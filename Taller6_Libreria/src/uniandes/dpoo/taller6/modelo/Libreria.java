package uniandes.dpoo.taller6.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.nio.file.Path;

/**
 * Esta clase agrupa toda la informaciï¿½n de una librerï¿½a: las cat
 * gorï¿½as 
 * que s
 * usan para clasificar los libros, y del catï¿½logo de libros.
 * Adicionalmente esta clase es capaz de calcular y hacer bï¿½squedas sobre l
 * s
 * categorï¿½as y sobre el catï¿½logo de libros.
 */
public class Libreria {

	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * El arreglo con las categorï¿½as que hay en la librerï¿½a
	 */
	private Categoria[] categorias;

	/**
	 * Una lista con los libros disponibles en la librerï¿½a
	 */
	private ArrayList<Libro> catalogo;

	/**
	 * Una lista con las categorias creadas tipo String
	 */
	private ArrayList<String> creadas = new ArrayList<String>();

	/*
	 * Una lista de categorias como objetos creados
	 */
	private ArrayList<Categoria> creadasObjeto = new ArrayList<Categoria>();

	boolean borraronDatos = false;

	// ************************************************************************
	// Constructores
	// ************************************************************************

	/**
	 * Construye una nueva librerï¿½a a partir de la informaciï¿½n de los par
	 * metros y
	 * de la informaciï¿½n contenida en los archivos.
	 * 
	 * @param nombreArchivoCategorias El nombre del archivo CSV que
	 * @param nombreArchivoLibros     El nombre del archivo CSV que tiene la
	 *                                informaciï¿½n sobre los libros
	 * @throws IOException Lanza esta excepciï¿½n si hay algï¿½n problema ley
	 *                     ndo un
	 *                     archivo
	 */
	public Libreria(String nombreArchivoCategorias, String nombreArchivoLibros) throws IOException {
		this.categorias = cargarCategorias(nombreArchivoCategorias);
		this.catalogo = cargarCatalogo(nombreArchivoLibros);
	}

	// ************************************************************************
	// MÃ©todos para consultar los atributos
	// ************************************************************************

	/*
	 * Retorna las categorias creadas
	 */
	public ArrayList<String> darCreadas() {
		return this.creadas;
	}

	/*
	 * Retorna categorias creadas
	 */
	public ArrayList<Categoria> getCreadasObjeto() {
		return this.creadasObjeto;
	}

	/**
	 * Retorna las categorï¿½as de la librerï¿½a
	 * 
	 * @return categorias
	 */
	public Categoria[] darCategorias() {
		return categorias;
	}

	/**
	 * Retorna el catï¿½logo completo de libros de la librerï¿½a
	 * 
	 * @return catalogo
	 */
	public ArrayList<Libro> darLibros() {
		return catalogo;
	}

	// ************************************************************************
	// Otros mÃ©todos
	// ************************************************************************

	/**
	 * Carga la informacion
	 * 
	 * @param nombreArchivoCategorias El nombre del archivo CSV que contiene la
	 *                                informaciï¿½n de las categorï¿½as
	 * 
	 * @throws IOException Se lanza esta excepciï¿½n s
	 *                     hay algï¿½n problema leyendo del
	 * @return arregloCategorias
	 *         Un arreglo con las categorï¿½as que se encontraron en el a
	 *         chivo
	 */
	private Categoria[] cargarCategorias(String nombreArchivoCategorias) throws IOException {

		ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();

		BufferedReader br = new BufferedReader(new FileReader(nombreArchivoCategorias));
		String linea = br.readLine(); // Ignorar la primera lï¿½nea porque tiene los tï¿½tulos

		linea = br.readLine();
		while (linea != null) {
			String[] partes = linea.trim().split(",");
			String nombreCat = partes[0];
			boolean esFiccion = partes[1].equals("true");

			// Crear una nueva categorï¿½a y agregarla a la lista
			listaCategorias.add(new Categoria(nombreCat, esFiccion));

			linea = br.readLine();
		}

		br.close();

		// Convertir la lista de categorï¿½as a un arreglo
		Categoria[] arregloCategorias = new Categoria[listaCategorias.size()];
		for (int i = 0; i < listaCategorias.size(); i++) {
			arregloCategorias[i] = listaCategorias.get(i);
		}

		return arregloCategorias;

	}

	/**
	 * Carga la informaciï¿½n sobre los libros disponibles en la librerï¿½a.
	 * 
	 * Se deben haber cargado antes las categorï¿½as e inicializado el atributo
	 * 'categorias'.
	 * 
	 * @param nombreArchivoLibros El nombre del archivo CSV que contiene la
	 *                            informaciï¿½n de los libros
	 * @throws IOException Se lanza esta excepciï¿½n s
	 *                     hay algï¿½n problema leyndo del
	 * @return libros
	 *         Una lista con los libros que se cargaron a partir del archivo
	 */
	private ArrayList<Libro> cargarCatalogo(String nombreArchivoLibros) throws IOException {

		ArrayList<Libro> libros = new ArrayList<Libro>();

		BufferedReader br = new BufferedReader(new FileReader(nombreArchivoLibros));

		// Ignorar la primera lï¿½nea porque tiene los tï¿½tulos:
		// Titulo,Autor,Calificacion,Categoria,Portada,Ancho,Alto
		String linea = br.readLine();

		linea = br.readLine();
		while (linea != null) {

			String[] partes = linea.trim().split(",");
			String elTitulo = partes[0];
			String elAutor = partes[1];
			double laCalificacion = Double.parseDouble(partes[2]);
			String nombreCategoria = partes[3]; // da categoria
			Categoria laCategoria; // Categoria como objeto

			try {
				laCategoria = buscarCategoria(nombreCategoria);
			} catch (Exception e) {
				creadas.add(nombreCategoria);
				laCategoria = new Categoria(nombreCategoria, true); // excepciones
				creadasObjeto.add(laCategoria);

				Categoria[] categoriasnueva = new Categoria[this.categorias.length + 1];
				int i = 0;
				for (Categoria categoria : this.categorias) {
					categoriasnueva[i] = categoria;
					i++;
				}

				categoriasnueva[i] = laCategoria;

				this.categorias = categoriasnueva;

			}

			String archivoPortada = partes[4];
			String ancho1 = partes[5].replace("\"", ""); // cambias ancho alto
			String alto1 = partes[6].replace("\"", "");
			int ancho = Integer.parseInt(ancho1);
			int alto = Integer.parseInt(alto1);

			// Crear un nuevo libro
			Libro nuevo = new Libro(elTitulo, elAutor, laCalificacion, laCategoria);
			libros.add(nuevo);

			// Si existe el archivo de la portada, ponÃ©rselo al libro
			if (existeArchivo(archivoPortada)) {
				Imagen portada = new Imagen(archivoPortada, ancho, alto);
				nuevo.cambiarPortada(portada);
			}

			linea = br.readLine();
		}

		br.close();

		return libros;

	}

	/**
	 * Busca una categorï¿½a a partir de su nombre
	 * 
	 * @param nombreCategoria El nombre de la categorï¿½a buscada
	 * @return La categorï¿½a que tiene el nombre dado
	 * @throws Exception
	 */
	private Categoria buscarCategoria(String nombreCategoria) throws Exception {
		Categoria laCategoria = null;
		for (int i = 0; i < categorias.length && laCategoria == null; i++) {
			if (categorias[i].darNombre().equals(nombreCategoria))
				laCategoria = categorias[i];
		}

		if (laCategoria == null) {

			throw new Exception("No se encontrï¿½ la categorï¿½a del libro"); // no se encontro categoria
		}

		return laCategoria;
	}

	/**
	 * Verifica si existe el archivo con el nombre indicado dentro de la carpeta
	 * "data".
	 * 
	 * @param nombreArchivo El nombre del archivo que se va a buscar.
	 * @return
	 */
	private boolean existeArchivo(String nombreArchivo) {
		String path = Path.of("").toAbsolutePath().toString() + "/Taller6_Libreria/data/";
		File archivo = new File(path + nombreArchivo);
		// File archivo = new File("./data/" + nombreArchivo);
		return archivo.exists();
	}

	/**
	 * 
	 * @param nombreCategoria
	 * @return
	 */
	public void renombrarCategoria(String antiguoNombreCategoria, String nuevoNombreCategoria) throws Exception {

		boolean existeCategoria = false;

		int posicionCategoria = -1;

		for (int i = 0; i < categorias.length; i++) {

			Categoria categoria1 = categorias[i];
			String nombreCategoria = categoria1.darNombre();

			if (antiguoNombreCategoria.equals(nombreCategoria)) {
				posicionCategoria = i;
			}

			if (nuevoNombreCategoria.equals(nombreCategoria)) {
				existeCategoria = true;
			}

		}
		if (existeCategoria == false && posicionCategoria != -1) {
			categorias[posicionCategoria].renombrar(nuevoNombreCategoria);
		} else {
			throw new Exception("El nombre de la categoría ya se encuentra asignado");
		}

	}

	/*
	 * Borrar libros
	 */
	public void borrarLibros(String autoresString) throws Exception {

		boolean valido = true;

		String[] autores = autoresString.split(",");

		for (int i = 0; i < autores.length; i++) {

			String autor = autores[i].trim();
			ArrayList<Libro> librosAutor = buscarLibrosAutor(autor);

			if (librosAutor.size() <= 0) {
				valido = false;
			}
		}

		if (valido) {

			for (int i = 0; i < autores.length; i++) {

				String autor = autores[i].trim();

				ArrayList<Libro> librosAutor = buscarLibrosAutor(autor);

				for (int j = 0; j < librosAutor.size(); j++) {

					Libro libro = librosAutor.get(j);

					catalogo.remove(libro);

					// Eliminar de categorias
					for (int k = 0; k < categorias.length; k++) {
						Categoria categoria = categorias[k];
						ArrayList<Libro> libros = categoria.darLibros();

						boolean contiene = libros.contains(libro);

						if (contiene) {

							libros.remove(libro);
							contiene = libros.contains(libro);

						}
					}

				}

				librosAutor = buscarLibrosAutor(autor);

			}

		} else {
			throw new Exception("Al menos uno de los autores no tiene libros en el catalogo");
		}
	}

	/**
	 * Retorna una lista con los libros que pertenecen a la categoria
	 * 
	 * @param nombreCategoria El nombre de la categorï¿½a de interÃ©s
	 * 
	 * @return seleccionados
	 *         Una lista donde todos los libros pertenecen a la categorï¿½a i
	 *         dicada
	 */
	public ArrayList<Libro> darLibros(String nombreCategoria) {
		boolean encontreCategoria = false;

		ArrayList<Libro> seleccionados = new ArrayList<Libro>();

		for (int i = 0; i < categorias.length && !encontreCategoria; i++) {
			if (categorias[i].darNombre().equals(nombreCategoria)) {
				encontreCategoria = true;
				seleccionados.addAll(categorias[i].darLibros());
			}
		}

		return seleccionados;
	}

	/**
	 * Busca un libro a partir de su tï¿½tulo
	 * 
	 * @param tituloLibro Tï¿½tulo del libro buscado
	 * 
	 * @return libroBuscado
	 *         Retorna un libro con
	 *         el tï¿½tulo indicado o null si no se encontrï¿½ un
	 *         libro con ese tï¿½tulo
	 */
	public Libro buscarLibro(String tituloLibro) {
		Libro libroBuscado = null;

		for (int i = 0; i < catalogo.size() && libroBuscado == null; i++) {
			Libro unLibro = catalogo.get(i);
			if (unLibro.darTitulo().equals(tituloLibro))
				libroBuscado = unLibro;
		}

		return libroBuscado;
	}

	/**
	 * Busca en la librerï¿½a los libros escritos por el autor indicado
	 * El nombre del autor puede estar incompleto
	 * no debe cuenta mayï¿½sculas y minï¿½sculas. Por ejemplo, si se buscara 
	 * por "ulio
	 * deberï¿½an encontrarse los libros donde el autor sea "Julio Verne".
	 * 
	 * @param cadenaAutor La cadena que se usarï¿½ para consultar el autor. No
	 *                    necesariamente corresponde al nombre completo de un autor.
	 * @return Una lista con todos los libros cuyo autor coincida con la cadena
	 *         indicada
	 */
	public ArrayList<Libro> buscarLibrosAutor(String cadenaAutor) {
		ArrayList<Libro> librosAutor = new ArrayList<Libro>();

		for (int i = 0; i < categorias.length; i++) {
			ArrayList<Libro> librosCategoria = categorias[i].buscarLibrosDeAutor(cadenaAutor);
			if (!librosCategoria.isEmpty()) {
				librosAutor.addAll(librosCategoria);
			}
		}

		return librosAutor;
	}

	/**
	 * Busca en quÃ© categorï¿½as hay libros del autor indicado.
	 * 
	 * Este mÃ©todo busca libros cuyo autor coincida exactamente con el valor
	 * indicado en el parï¿½metro nombreAutor.
	 * 
	 * @param nombreAutor El nombre del autor
	 * @return Una lista con las categorï¿½as en las cuales hay al menos un libro
	 *         del autor indicado. Si no hay un libro del autor en ninguna
	 *         categorï¿½a,
	 *         retorna una lista vacï¿½a.
	 */
	public ArrayList<Categoria> buscarCategoriasAutor(String nombreAutor) {
		ArrayList<Categoria> resultado = new ArrayList<Categoria>();

		for (int i = 0; i < categorias.length; i++) {
			if (categorias[i].hayLibroDeAutor(nombreAutor)) {
				resultado.add(categorias[i]);
			}
		}

		return resultado;
	}

	/**
	 * Calcula la calificaciï¿½n promedio calculada entre todos los libros del
	 * catï¿½logo
	 * 
	 * @return Calificaciï¿½n promedio del catï¿½logo
	 */
	public double calificacionPromedio() {
		double total = 0;

		for (Libro libro : catalogo) {
			total += libro.darCalificacion();
		}

		return total / (double) catalogo.size();
	}

	/**
	 * Busca cuï¿½l es la categorï¿½a que tiene mï¿½s libros
	 * 
	 * @return categoriaGanadora
	 *         La categorï¿½a con mï¿½s libros. Si hay empate, retorna cua
	 *         quiera de
	 *         las
	 *         que estÃ©n empatadas en el primer lugar. Si no hay ningï¿½n libr
	 *         ,
	 *         retorna null.
	 */
	public Categoria categoriaConMasLibros() {
		int mayorCantidad = -1;
		Categoria categoriaGanadora = null;

		for (int i = 0; i < categorias.length; i++) {
			Categoria cat = categorias[i];
			if (cat.contarLibrosEnCategoria() > mayorCantidad) {
				mayorCantidad = cat.contarLibrosEnCategoria();
				categoriaGanadora = cat;
			}
		}
		return categoriaGanadora;
	}

	/**
	 * Busca cuï¿½l es la categorï¿½a cuyos libros tienen el
	 * mayor promedio en su calificaciï¿½n
	 * 
	 * @return Categorï¿½a con los mejores libros
	 */
	public Categoria categoriaConMejoresLibros() {
		double mejorPromedio = -1;
		Categoria categoriaGanadora = null;

		for (int i = 0; i < categorias.length; i++) {
			Categoria cat = categorias[i];
			double promedioCat = cat.calificacionPromedio();
			if (promedioCat > mejorPromedio) {
				mejorPromedio = promedioCat;
				categoriaGanadora = cat;
			}
		}
		return categoriaGanadora;
	}

	/**
	 * Cuenta cuï¿½ntos libros del catï¿½logo no tienen portada
	 * 
	 * @return Cantidad de libros sin portada
	 */
	public int contarLibrosSinPortada() {
		int cantidad = 0;
		for (Libro libro : catalogo) {
			if (!libro.tienePortada()) {
				cantidad++;
			}
		}
		return cantidad;
	}

	/**
	 * Consulta si hay algï¿½n autor que tenga un libro en mï¿½s de una cat
	 * gorï¿½a
	 * 
	 * @return Retorna true si hay algï¿½n autor que tenga al menos un libro en d
	 *         s
	 *         categorï¿½as diferentes. Retorna false en caso contrario.
	 */
	public boolean hayAutorEnVariasCategorias() {
		boolean hayAutorEnVariasCategorias = false;

		HashMap<String, HashSet<String>> categoriasAutores = new HashMap<>();

		for (int i = 0; i < catalogo.size() && !hayAutorEnVariasCategorias; i++) {
			Libro libro = catalogo.get(i);
			String autor = libro.darAutor();
			String nombreCategoria = libro.darCategoria().darNombre();

			if (!categoriasAutores.containsKey(autor)) {
				HashSet<String> categoriasAutor = new HashSet<String>();
				categoriasAutor.add(nombreCategoria);
				categoriasAutores.put(autor, categoriasAutor);
			} else {
				HashSet<String> categoriasAutor = categoriasAutores.get(autor);
				if (!categoriasAutor.contains(nombreCategoria)) {
					categoriasAutor.add(nombreCategoria);
					hayAutorEnVariasCategorias = true;
				}
			}
		}

		return hayAutorEnVariasCategorias;

	}

	/*
	 * BORRARRRRRR
	 */

	public static void main(String[] args) throws IOException {

		// String path = Path.of("").toAbsolutePath().toString() +
		// "/Taller6_Libreria/data/";

		// String nombreArchivoCategorias = path + "categorias.csv";
		// String nombreArchivoLibros = path + "libreria.csv";
		// File a = new File(nombreArchivoCategorias);

		// Libreria libreria = new Libreria(nombreArchivoCategorias,
		// nombreArchivoLibros);

		// System.out.println(libreria.categorias.length);
		// try {
		// // libreria.renombrarCategoria("Computing", "CS");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

}
