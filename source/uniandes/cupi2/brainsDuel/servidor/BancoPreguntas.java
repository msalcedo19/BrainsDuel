package uniandes.cupi2.brainsDuel.servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que representa al banco de preguntas.
 */
public class BancoPreguntas {

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------
	/**
	 * Constante que representa la categoria deportes.
	 */
	private static final String DEPORTES = "DEPORTES";

	/**
	 * Constante que representa la categoria entretenimiento.
	 */
	private static final String ENTRETENIMIENTO = "ENTRETENIMIENTO";

	/**
	 * Constante que representa la categoria ciencia.
	 */
	private static final String CIENCIA = "CIENCIA";

	/**
	 * Constante que representa la categoria historia.
	 */
	private static final String HISTORIA = "HISTORIA";

	/**
	 * Constante que representa la categoria geografia.
	 */
	private static final String GEOGRAFIA = "GEOGRAFIA";
	
	/**
	 * Constante que representa la categoria arte.
	 */
	private static final String ARTE = "ARTE";
	
	/**
	 * Constante que representa la categoria corona.
	 */
	private static final String CORONA = "CORONA";

	/**
	 * Representa un archivo.
	 */
	private File archivo;

	/**
	 * El flujo de lectura.
	 */
	private BufferedReader lector;

	/**
	 * arreglo que contiene las preguntas de la categoria deportes.
	 */
	private ArrayList<String> deportes;

	/**
	 * arreglo que contiene las preguntas de la categoria entretenimiento.
	 */
	private ArrayList<String> entretenimiento;

	/**
	 * arreglo que contiene las preguntas de la categoria ciencia.
	 */
	private ArrayList<String> ciencia;

	/**
	 * arreglo que contiene las preguntas de la categoria historia.
	 */
	private ArrayList<String> historia;

	/**
	 * arreglo que contiene las preguntas de la categoria geografia.
	 */
	private ArrayList<String> geografia;
	
	/**
	 * arreglo que contiene las preguntas de la categoria arte.
	 */
	private ArrayList<String> arte;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * COnstructor del banco de preguntas.
	 * @param pRuta ruta del archivo contenedor de las preguntas.
	 * @throws FileNotFoundException - lanza una excepcion si no encuentra el archivo.
	 */
	public BancoPreguntas(String pRuta) throws FileNotFoundException {

		archivo = new File(pRuta);
		FileReader file = new FileReader(archivo);
		lector = new BufferedReader(file);
		
		deportes = new ArrayList<String>();
		entretenimiento = new ArrayList<String>();
		ciencia = new ArrayList<String>();
		historia = new ArrayList<String>();
		geografia = new ArrayList<String>();
		arte = new ArrayList<String>();
		
		try {
			
			cargarPreguntas();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Carga cada pregunta al arreglo a la cual pertenece, dependiendo de su categoria.
	 * @throws IOException - lanza una excepcion si hay un problema leyendo el archivo.
	 */
	private void cargarPreguntas() throws IOException
	{
		String linea = lector.readLine();
		
		while(linea != null){
			
			String categoria = linea.split(";")[0];
			String añadir = linea.split(categoria + ";")[1];
			
			if(categoria.equals(DEPORTES))
			{
				añadir = añadir.replace(";", ":::");
				deportes.add(añadir);
			}
			else if(categoria.equals(ENTRETENIMIENTO))
			{
				añadir = añadir.replace(";", ":::");
				entretenimiento.add(añadir);
			}
			else if(categoria.equals(CIENCIA))
			{
				añadir = añadir.replace(";", ":::");
				ciencia.add(añadir);
			}
			else if(categoria.equals(HISTORIA))
			{
				añadir = añadir.replace(";", ":::");
				historia.add(añadir);
			}
			else if(categoria.equals(GEOGRAFIA))
			{
				añadir = añadir.replace(";", ":::");
				geografia.add(añadir);
			}
			else if(categoria.equals(ARTE))
			{
				añadir = añadir.replace(";", ":::");
				arte.add(añadir);
			}

			linea = lector.readLine();
		}


	}
	
	/**
	 * Retorna el arreglo con la categoria especificada.
	 * @param pCategoria categoria del arreglo.
	 * @return retorna un arreglo.
	 */
	public ArrayList<String> darArregloPreguntas(String pCategoria)
	{
		if(pCategoria.equals(DEPORTES))
		{
			return deportes;
		}
		else if(pCategoria.equals(ENTRETENIMIENTO))
		{
			return entretenimiento;
		}
		else if(pCategoria.equals(CIENCIA))
		{
			return ciencia;
		}
		else if(pCategoria.equals(HISTORIA))
		{
			return historia;
		}
		else if(pCategoria.equals(GEOGRAFIA))
		{
			return geografia;
		}
		else if(pCategoria.equals(ARTE))
		{
			return arte;
		}
		else if(pCategoria.equals(CORONA))
		{
			return darArregloAleatorio();
		}
		
		return null;
	}
	
	/**
	 * Devuelve un arreglo aleatorio.
	 * @return retorna un arreglo.
	 */
	public ArrayList<String> darArregloAleatorio()
	{
		int numero = generarNumeroAleatorioEnRango(6) + 1;
		
		ArrayList<String> p = null;
		
		if(numero == 1)
		{
			p = deportes;
		}
		else if(numero == 2)
		{
			p = entretenimiento;
		}
		else if(numero == 3)
		{
			p = ciencia;
		}
		else if(numero == 4)
		{
			p = historia;
		}
		
		else if(numero == 5)
		{
			p = geografia;
		}
		else if(numero == 6)
		{
			p = arte;
		}
		
		return p;
	}
	
	/**
	 * Genera un número entero aleatorio entre 0 y tamRango - 1
	 * @param tamRango Tamaño del rango
	 * @return Número entero entre 0 y tamRango - 1
	 */
	private int generarNumeroAleatorioEnRango( int tamRango )
	{
		return ( int ) ( Math.random( ) * tamRango );
	}

}