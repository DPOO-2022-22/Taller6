package uniandes.dpoo.taller6.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Esta clase agrupa toda la informaci�n de una librer�a: las categor�as que se
 * usan para clasificar los libros, y del cat�logo de libros.
 * 
 * Adicionalmente esta clase es capaz de calcular y hacer b�squedas sobre las
 * categor�as y sobre el cat�logo de libros.
 */
public class Libreria
{
	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * El arreglo con las categor�as que hay en la librer�a
	 */
	private Categoria[] categorias;

	/**
	 * Una lista con los libros disponibles en la librer�a
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
	
	boolean borraronDatos =false;
	

	
	// ************************************************************************
	// Constructores
	// ************************************************************************

	/**
	 * Construye una nueva librer�a a partir de la informaci�n de los par�metros y
	 * de la informaci�n contenida en los archivos.
	 * 
	 * @param nombreArchivoCategorias El nombre del archivo CSV que tiene la
	 *                                informaci�n sobre las categor�as de libros
	 * @param nombreArchivoLibros     El nombre del archivo CSV que tiene la
	 *                                informaci�n sobre los libros
	 * @throws IOException Lanza esta excepci�n si hay alg�n problema leyendo un
	 *                     archivo
	 */
	public Libreria(String nombreArchivoCategorias, String nombreArchivoLibros) throws IOException
	{
		this.categorias = cargarCategorias(nombreArchivoCategorias);
		this.catalogo = cargarCatalogo(nombreArchivoLibros);
	}

	// ************************************************************************
	// Métodos para consultar los atributos
	// ************************************************************************

	/*
	 * Retorna las categorias creadas
	 * 
	 */
	public ArrayList<String> darCreadas() { 
		return this.creadas;
	}
	/**
	 * Retorna las categor�as de la librer�a
	 * 
	 * @return categorias
	 */
	public Categoria[] darCategorias()
	{
		return categorias;
	}

	/**
	 * Retorna el cat�logo completo de libros de la librer�a
	 * 
	 * @return catalogo
	 */
	public ArrayList<Libro> darLibros()
	{
		return catalogo;
	}

	// ************************************************************************
	// Otros métodos
	// ************************************************************************

	/**
	 * Carga la informaci�n sobre las categor�as disponibles a partir de un archivo
	 * 
	 * @param nombreArchivoCategorias El nombre del archivo CSV que contiene la
	 *                                informaci�n de las categor�as
	 * @return Un arreglo con las categor�as que se encontraron en el archivo
	 * @throws IOException Se lanza esta excepci�n si hay alg�n problema leyendo del
	 *                     archivo
	 */
	private Categoria[] cargarCategorias(String nombreArchivoCategorias) throws IOException
	{
		ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();

		BufferedReader br = new BufferedReader(new FileReader(nombreArchivoCategorias));
		String linea = br.readLine(); // Ignorar la primera l�nea porque tiene los t�tulos

		linea = br.readLine();
		while (linea != null)
		{
			String[] partes = linea.trim().split(",");
			String nombreCat = partes[0];
			boolean esFiccion = partes[1].equals("true");

			// Crear una nueva categor�a y agregarla a la lista
			listaCategorias.add(new Categoria(nombreCat, esFiccion));

			linea = br.readLine();
		}

		br.close();

		// Convertir la lista de categor�as a un arreglo
		Categoria[] arregloCategorias = new Categoria[listaCategorias.size()];
		for (int i = 0; i < listaCategorias.size(); i++)
		{
			arregloCategorias[i] = listaCategorias.get(i);
		}

		return arregloCategorias;
	}

	/**
	 * Carga la informaci�n sobre los libros disponibles en la librer�a.
	 * 
	 * Se deben haber cargado antes las categor�as e inicializado el atributo
	 * 'categorias'.
	 * 
	 * @param nombreArchivoLibros El nombre del archivo CSV que contiene la
	 *                            informaci�n de los libros
	 * @return Una lista con los libros que se cargaron a partir del archivo
	 * @throws IOException Se lanza esta excepci�n si hay alg�n problema leyendo del
	 *                     archivo
	 */
	private ArrayList<Libro> cargarCatalogo(String nombreArchivoLibros) throws IOException
	{
		ArrayList<Libro> libros = new ArrayList<Libro>();

		BufferedReader br = new BufferedReader(new FileReader(nombreArchivoLibros));
		String linea = br.readLine(); // Ignorar la primera l�nea porque tiene los t�tulos:
										// Titulo,Autor,Calificacion,Categoria,Portada,Ancho,Alto

		linea = br.readLine();
		while (linea != null)
		{
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
				laCategoria = new Categoria(nombreCategoria, true);  //excepciones
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
			String ancho1 = partes[5].replace("\"", "");  //cambias ancho alto 
			String alto1= partes[6].replace("\"", "");
			int ancho = Integer.parseInt(ancho1);
			int alto = Integer.parseInt(alto1);

			// Crear un nuevo libro
			Libro nuevo = new Libro(elTitulo, elAutor, laCalificacion, laCategoria);
			libros.add(nuevo);

			// Si existe el archivo de la portada, ponérselo al libro
			if (existeArchivo(archivoPortada))
			{
				Imagen portada = new Imagen(archivoPortada, ancho, alto);
				nuevo.cambiarPortada(portada);
			}

			linea = br.readLine();
		}

		br.close();

		return libros;
	}

	/**
	 * Busca una categor�a a partir de su nombre
	 * 
	 * @param nombreCategoria El nombre de la categor�a buscada
	 * @return La categor�a que tiene el nombre dado
	 */
	private Categoria buscarCategoria(String nombreCategoria)
	{
		Categoria laCategoria = null;
		for (int i = 0; i < categorias.length && laCategoria == null; i++)
		{
			if (categorias[i].darNombre().equals(nombreCategoria))
				laCategoria = categorias[i];
		}
		
		if (laCategoria == null)
		{
			
			throw new Exception ("No se encontr� la categor�a del libro"); // no se encontro categoria
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
	private boolean existeArchivo(String nombreArchivo)
	{
		File archivo = new File("./data/" + nombreArchivo);
		return archivo.exists();
	}

	/**
	 * Retorna una lista con los libros que pertenecen a la categor�a indicada en el
	 * par�metro
	 * 
	 * @param nombreCategoria El nombre de la categor�a de interés
	 * @return Una lista donde todos los libros pertenecen a la categor�a indicada
	 */
	public ArrayList<Libro> darLibros(String nombreCategoria)
	{
		boolean encontreCategoria = false;

		ArrayList<Libro> seleccionados = new ArrayList<Libro>();

		for (int i = 0; i < categorias.length && !encontreCategoria; i++)
		{
			if (categorias[i].darNombre().equals(nombreCategoria))
			{
				encontreCategoria = true;
				seleccionados.addAll(categorias[i].darLibros());
			}
		}

		return seleccionados;
	}

	/**
	 * Busca un libro a partir de su t�tulo
	 * 
	 * @param tituloLibro T�tulo del libro buscado
	 * @return Retorna un libro con el t�tulo indicado o null si no se encontr� un
	 *         libro con ese t�tulo
	 */
	public Libro buscarLibro(String tituloLibro)
	{
		Libro libroBuscado = null;

		for (int i = 0; i < catalogo.size() && libroBuscado == null; i++)
		{
			Libro unLibro = catalogo.get(i);
			if (unLibro.darTitulo().equals(tituloLibro))
				libroBuscado = unLibro;
		}

		return libroBuscado;
	}

	/**
	 * Busca en la librer�a los libros escritos por el autor indicado.
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
	public ArrayList<Libro> buscarLibrosAutor(String cadenaAutor)
	{
		ArrayList<Libro> librosAutor = new ArrayList<Libro>();

		for (int i = 0; i < categorias.length; i++)
		{
			ArrayList<Libro> librosCategoria = categorias[i].buscarLibrosDeAutor(cadenaAutor);
			if (!librosCategoria.isEmpty())
			{
				librosAutor.addAll(librosCategoria);
			}
		}

		return librosAutor;
	}

	/**
	 * Busca en qué categor�as hay libros del autor indicado.
	 * 
	 * Este método busca libros cuyo autor coincida exactamente con el valor
	 * indicado en el par�metro nombreAutor.
	 * 
	 * @param nombreAutor El nombre del autor
	 * @return Una lista con las categor�as en las cuales hay al menos un libro del
	 *         autor indicado. Si no hay un libro del autor en ninguna categor�a,
	 *         retorna una lista vac�a.
	 */
	public ArrayList<Categoria> buscarCategoriasAutor(String nombreAutor)
	{
		ArrayList<Categoria> resultado = new ArrayList<Categoria>();

		for (int i = 0; i < categorias.length; i++)
		{
			if (categorias[i].hayLibroDeAutor(nombreAutor))
			{
				resultado.add(categorias[i]);
			}
		}

		return resultado;
	}

	/**
	 * Calcula la calificaci�n promedio calculada entre todos los libros del
	 * cat�logo
	 * 
	 * @return Calificaci�n promedio del cat�logo
	 */
	public double calificacionPromedio()
	{
		double total = 0;

		for (Libro libro : catalogo)
		{
			total += libro.darCalificacion();
		}

		return total / (double) catalogo.size();
	}

	/**
	 * Busca cu�l es la categor�a que tiene m�s libros
	 * 
	 * @return La categor�a con m�s libros. Si hay empate, retorna cualquiera de las
	 *         que estén empatadas en el primer lugar. Si no hay ning�n libro,
	 *         retorna null.
	 */
	public Categoria categoriaConMasLibros()
	{
		int mayorCantidad = -1;
		Categoria categoriaGanadora = null;

		for (int i = 0; i < categorias.length; i++)
		{
			Categoria cat = categorias[i];
			if (cat.contarLibrosEnCategoria() > mayorCantidad)
			{
				mayorCantidad = cat.contarLibrosEnCategoria();
				categoriaGanadora = cat;
			}
		}
		return categoriaGanadora;
	}

	/**
	 * Busca cu�l es la categor�a cuyos libros tienen el mayor promedio en su
	 * calificaci�n
	 * 
	 * @return Categor�a con los mejores libros
	 */
	public Categoria categoriaConMejoresLibros()
	{
		double mejorPromedio = -1;
		Categoria categoriaGanadora = null;

		for (int i = 0; i < categorias.length; i++)
		{
			Categoria cat = categorias[i];
			double promedioCat = cat.calificacionPromedio();
			if (promedioCat > mejorPromedio)
			{
				mejorPromedio = promedioCat;
				categoriaGanadora = cat;
			}
		}
		return categoriaGanadora;
	}

	/**
	 * Cuenta cu�ntos libros del cat�logo no tienen portada
	 * 
	 * @return Cantidad de libros sin portada
	 */
	public int contarLibrosSinPortada()
	{
		int cantidad = 0;
		for (Libro libro : catalogo)
		{
			if (!libro.tienePortada())
			{
				cantidad++;
			}
		}
		return cantidad;
	}

	/**
	 * Consulta si hay alg�n autor que tenga un libro en m�s de una categor�a
	 * 
	 * @return Retorna true si hay alg�n autor que tenga al menos un libro en dos
	 *         categor�as diferentes. Retorna false en caso contrario.
	 */
	public boolean hayAutorEnVariasCategorias()
	{
		boolean hayAutorEnVariasCategorias = false;

		HashMap<String, HashSet<String>> categoriasAutores = new HashMap<>();

		for (int i = 0; i < catalogo.size() && !hayAutorEnVariasCategorias; i++)
		{
			Libro libro = catalogo.get(i);
			String autor = libro.darAutor();
			String nombreCategoria = libro.darCategoria().darNombre();

			if (!categoriasAutores.containsKey(autor))
			{
				HashSet<String> categoriasAutor = new HashSet<String>();
				categoriasAutor.add(nombreCategoria);
				categoriasAutores.put(autor, categoriasAutor);
			}
			else
			{
				HashSet<String> categoriasAutor = categoriasAutores.get(autor);
				if (!categoriasAutor.contains(nombreCategoria))
				{
					categoriasAutor.add(nombreCategoria);
					hayAutorEnVariasCategorias = true;
				}
			}
		}

		return hayAutorEnVariasCategorias;
	}

}
