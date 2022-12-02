package uniandes.dpoo.taller6.modelo;

import java.util.ArrayList;

/**
 * Esta clase representa a una categor�a de libros dentro de la librer�a. Cada
 * categor�a tiene un nombre �nico y adem�s sabe si es una categor�a a la que
 * pertenecen libros de ficci�n o no.
 */
public class Categoria {
	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * Nombre de la categor�a
	 */
	private String nombre;

	/**
	 * Indica si la categor�a corresponde a libros de ficci�n o no
	 */
	private boolean ficcion;

	/**
	 * Lista de libros que hacen parte de la categor�a
	 */
	private ArrayList<Libro> libros;

	// ************************************************************************
	// Constructores
	// ************************************************************************

	public Categoria(String nombre, boolean ficcion) {
		this.nombre = nombre;
		this.ficcion = ficcion;
		this.libros = new ArrayList<Libro>();
	}

	// ************************************************************************
	// Métodos para consultar los atributos
	// ************************************************************************

	/**
	 * Consulta el nombre de la categor�a
	 * 
	 * @return Categoria
	 */
	public String darNombre() {
		return nombre;
	}

	/**
	 * Consulta si esta es una categor�a de ficci�n o no, con base en el atributo
	 * ficcion.
	 * 
	 * @return ficcion
	 */
	public boolean esFiccion() {
		return ficcion;
	}

	/**
	 * Retorna la lista de libros que hacen parte de la categor�a
	 * 
	 * @return libros
	 */
	public ArrayList<Libro> darLibros() {
		return libros;
	}

	// ************************************************************************
	// Otros métodos
	// ************************************************************************

	public void renombrar(String nuevoNombre) // funcion renombrar
	{
		this.nombre = nuevoNombre;
	}

	/**
	 * Agrega un nuevo libro a la categor�a
	 * 
	 * @param nuevoLibro El nuevo libro que se va a agregar.
	 */
	public void agregarLibro(Libro nuevoLibro) {
		libros.add(nuevoLibro);
	}

	/**
	 * Cuenta la cantidad de libros en la categor�a
	 * 
	 * @return Cantidad de libros
	 */
	public int contarLibrosEnCategoria() {
		return libros.size();
	}

	/**
	 * Calcula la calificaci�n promedio de los libros que pertenecen a la categor�a
	 * 
	 * @return Calificaci�n promedio
	 */
	public double calificacionPromedio() {
		double total = 0;

		for (Libro libro : libros) {
			total += libro.darCalificacion();
		}

		return total / (double) libros.size();
	}

	/**
	 * Consulta si en la categor�a hay alg�n libro escrito por el autor indicado.
	 * 
	 * La b�squeda del autor se hace de forma exacta (tiene que ser idéntico al
	 * valor indicado en el par�metro nombreAutor).
	 * 
	 * @param nombreAutor El nombre del autor para el que se quiere hacer la
	 *                    b�squeda.
	 * @return Retorna true si hay al menos un libro cuyo autor es el autor buscado.
	 *         Retorna false de lo contrario.
	 */
	public boolean hayLibroDeAutor(String nombreAutor) {
		boolean hayLibro = false;

		int i = 0;
		while (i < libros.size() && !hayLibro) {
			hayLibro = libros.get(i).darAutor().equals(nombreAutor);
			i++;
		}

		return hayLibro;
	}

	/**
	 * Busca en la categor�a los libros escritos por el autor indicado.
	 * 
	 * El nombre del autor puede estar incompleto, y la b�squeda no debe tener en
	 * cuenta may�sculas y min�sculas. Por ejemplo, si se buscara por "ulio v"
	 * deber�an encontrarse los libros donde el autor sea "Julio Verne".
	 * 
	 * @param cadenaAutor La cadena que se usar� para consultar el autor. No
	 *                    necesariamente corresponde al nombre completo de un autor.
	 * @return Una lista con todos los libros cuyo autor coincida con la cadena
	 *         indicada
	 */
	public ArrayList<Libro> buscarLibrosDeAutor(String nombreAutor) {
		String cadena = nombreAutor.toLowerCase();

		ArrayList<Libro> librosAutor = new ArrayList<Libro>();

		for (Libro libro : libros) {
			if (libro.darAutor().toLowerCase().contains(cadena)) {
				librosAutor.add(libro);
			}
		}

		return librosAutor;
	}

	// ************************************************************************
	// Métodos sobrecargados
	// ************************************************************************

	/**
	 * Este método permite representar una categor�a como una cadena de caracteres
	 */
	@Override
	public String toString() {
		return nombre;
	}

}
