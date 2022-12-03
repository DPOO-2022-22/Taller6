package uniandes.dpoo.taller6.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller6.modelo.*;



public class LibroTest 
{

	/**
	 * Atributo usado para las pruebas
	 */
	private Libro libro;
	

	@BeforeAll
	public static void setUp()
	{
		// Inicialice el atributo de libro
		libro = new Libro();
	}
	
	@AfterAll
	public static void tearDown()
	{
		// Implemente el m�todo para que al ser llamado, se asegure de dejar el atributo libro igual que en el estado inicial del setUp.
	}
	
	@Test
	public void testDarTitulo()
	{
		fail("Implemente el test usando aserciones");
	}

	@Test
	public void testDarAutor()
	{
		fail("Implemente el test usando aserciones");
	}
	
	@Test
	public void testDarCalificacion()
	{
		fail("Implemente el test usando aserciones");
	}
	
	@Test
	public void testDarCategoria()
	{
		fail("Implemente el test usando aserciones");
	}
	
	@Test
	public void testDarPortada()
	{
		fail("Implemente el test usando aserciones");
	}
	
	@Test
	public void testCambiarPortada()
	{
		fail("Implemente el test usando aserciones");
	}
	
	@Test
	public void testTienePortada()
	{
		fail("Implemente el test usando aserciones");
	}
}
