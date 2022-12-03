package uniandes.dpoo.taller6.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller6.modelo.Imagen;

public class ImagenTest
{
	/**
	 * Atributo usado para las pruebas
	 */
	private Imagen imagen;
	

	@BeforeAll
	public void setUp()
	{
		// Inicialice el atributo de imagen
		imagen = new Imagen();
	}
	
	@AfterAll
	public static void tearDown()
	{
		// Implemente el m�todo para que al ser llamado, se asegure de dejar el atributo imagen igual que en el estado inicial del setUp.
	}
	
	@Test
	public void testDarRutaArchivo()
	{
		fail("Implemente el test usando aserciones");
	}

	@Test
	public void testDarAncho()
	{
		fail("Implemente el test usando aserciones");
	}

	@Test
	public void testDarAlto()
	{
		fail("Implemente el test usando aserciones");
	}
	
}
