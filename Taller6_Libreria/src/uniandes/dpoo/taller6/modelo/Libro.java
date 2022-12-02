package uniandes.dpoo.taller6.modelo;

/**
 * Esta clase agrupa la informaci�n sobre un libro disponible en la librer�a
 */
public class Libro {

	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * T�tulo del libro
	 */
	private String titulo;

	/**
	 * Autor o autores del libro
	 */
	private String autor;

	/**
	 * Calificaci�n obtenida por el libro en el sitio bookdepository.com
	 */
	private double calificacion;

	/**
	 * Categor�a a la que pertenece el libro
	 */
	private Categoria categoria;

	/**
	 * Imagen con la portada del libro
	 */
	private Imagen portada;

	// ************************************************************************
	// Constructores
	// ************************************************************************

	/**
	 * Construye un nuevo libro, sin portada, a partir de los par�metros.
	 * 
	 * La portada se inicializa en null.
	 * 
	 * Adem�s de inicializar los atributos del libro, agrega el libro que se e
	 * t�
	 * creando a la categor�a usando el m�todo agregarLibro de la clase Cat
	 * gor�a.
	 * 
	 * @param elTitulo       T�tulo del libro
	 * @param elAutor        Autor o autores del libro
	 * @param laCalificacion Calificaci�n obtenida por el libro en
	 *                       bookdepository.com
	 * @param laCategoria    Categor�a a la que pertenece el libro
	 */
	public Libro(String elTitulo, String elAutor, double laCalificacion, Categoria laCategoria) {

		titulo = elTitulo;
		autor = elAutor;
		calificacion = laCalificacion;
		portada = null;
		categoria = laCategoria;
		categoria.agregarLibro(this);

	}

	// ************************************************************************
	// M�todos para consultar los atributos
	// ************************************************************************

	/**
	 * Consulta el t�tulo del libro
	 * 
	 * @return titulo
	 */
	public String darTitulo() {
		return titulo;
	}

	/**
	 * Consulta el autor del libro
	 * 
	 * @return autor
	 */
	public String darAutor() {
		return autor;
	}

	/**
	 * Consulta la calificaci�n del libro en bookdepository.com
	 * 
	 * @return calificacion
	 */
	public double darCalificacion() {
		return calificacion;
	}

	/**
	 * Consulta la categor�a del libro
	 * 
	 * @return categoria
	 */
	public Categoria darCategoria() {
		return categoria;
	}

	/**
	 * Consulta la imagen con la portada del libro. Si el libro no tiene una portada
	 * debe retornar la imagen por defecto de 85x85 pixeles que se encuentra en el
	 * archivo "data/missing.png"
	 * 
	 * @return La portada del libro o un objeto de tipo Imagen con la imagen del
	 *         archivo "./imagenes/missing.png"
	 */
	public Imagen darPortada() {
		Imagen laPortada = portada;
		if (laPortada == null) {
			laPortada = new Imagen("./imagenes/missing.png", 85, 85);
		}

		return laPortada;
	}

	// ************************************************************************
	// ************************************************************************

	/**
	 * Modifica la portada del libro
	 * 
	 * @param nuevaPortada Nueva portada para el libro
	 */
	public void cambiarPortada(Imagen nuevaPortada) {
		this.portada = nuevaPortada;
	}

	/**
	 * Permite saber si este libro tiene portada.
	 * 
	 * @return Retorna true si el libro tiene una portada. Retorna false en caso
	 *         contrario.
	 */
	public boolean tienePortada() {
		return portada != null;
	}

	@Override
	public String toString() {
		return titulo + " (" + autor + ")";
	}

	@Override
	public boolean equals(Object obj) {
		Libro otroLibro = (Libro) obj;

		return this.titulo.equals(otroLibro.titulo);
	}

}
