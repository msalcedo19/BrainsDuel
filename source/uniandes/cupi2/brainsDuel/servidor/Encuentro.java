package uniandes.cupi2.brainsDuel.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import uniandes.cupi2.brainsDuel.cliente.mundo.BrainsDuelException;
import uniandes.cupi2.brainsDuel.cliente.mundo.Duelo;

/**
 * Esta clase que administra un encuentro y mantiene la comunicación entre los dos jugadores. Esta clase define <br> 
 * la parte fija de los mensajes del protocolo de comunicación.<br>
 * Cada encuentro funciona en un thread diferente, permitiendo que en el mismo servidor se lleven a cabo <br> 
 * varios encuentros a la vez.<br>
 * <b>inv:</b><br>
 * !finJuego => socketJugador1 != null <br>
 * !finJuego => out1 != null <br>
 * !finJuego => in1 != null <br>
 * !finJuego => socketJugador2 != null <br>
 * !finJuego => out2 != null <br>
 * !finJuego => in2 != null <br>
 * jugador1 != null <br>
 * jugador2 != null <br>
 */
public class Encuentro extends Thread
{

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * El canal usado para comunicarse con el jugador 1
	 */
	private Socket socketJugador1;

	/**
	 * El flujo de escritura conectado con el jugador 1
	 */
	private PrintWriter out1;

	/**
	 * El flujo de lectura conectado con el jugador 1
	 */
	private BufferedReader in1;

	/**
	 * El flujo de escritura conectado con el jugador 2
	 */
	private PrintWriter out2;

	/**
	 * El flujo de lectura conectado con el jugador 2
	 */
	private BufferedReader in2;

	/**
	 * El objeto con la información sobre el jugador 1: visibilidad protegida, para facilitar las pruebas
	 */
	protected RegistroJugador jugador1;

	/**
	 * El objeto con la información sobre el jugador 2: visibilidad protegida, para facilitar las pruebas
	 */
	protected RegistroJugador jugador2;

	/**
	 * Es el administrador que permite registrar el resultado del encuentro sobre la base de datos y consultar la información de los jugadores
	 */
	private AdministradorJugadores adminJugadores;

	/**
	 * Indica que el encuentro debe terminar
	 */
	private boolean finJuego;

	/**
	 * Indica que el juego debe comenzar.
	 */
	private boolean inicioJuego;

	/**
	 * Es una referencia hacia el banco de preguntas.
	 */
	private BancoPreguntas preguntas;

	/**
	 * Hace rerefencia a la respuesta correcta a la pregunta en juego.
	 */
	private String respuestaCorrecta;

	/**
	 * Indica si acerto o no a la pregunta
	 */
	private boolean acerto;

	/**
	 * Indica el turno del jugador que empieza.
	 */
	private int turno;

	/**
	 * Indica si el estado de juego es en reto.
	 */
	private boolean reto;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------
	/**
	 * Establece la comunicación con los dos jugadores y el encuentro queda listo para iniciar
	 * @param jugador1 El socket para comunicarse con el jugador 1 - cliente1 != null
	 * @param jugador2 El socket para comunicarse con el jugador 2 - cliente2 != null
	 * @param administrador Es el objeto que permite consultar y registrar resultados sobre la base de datos - administrador != null
	 * @throws IOException Se lanza esta excepción si hay problemas estableciendo la comunicación con los dos jugadores
	 */
	public Encuentro( Socket canal1, BufferedReader in, PrintWriter out,  AdministradorJugadores administrador,RegistroJugador pJugador1, RegistroJugador pJugador2 , BancoPreguntas pPreguntas ) throws IOException
	{

		adminJugadores = administrador;
		jugador1 = pJugador1;
		jugador2 = pJugador2;

		out1 = new PrintWriter( canal1.getOutputStream( ), true );
		in1 = new BufferedReader( new InputStreamReader( canal1.getInputStream( ) ) );
		socketJugador1 = canal1;
		out2 = out;
		in2 = in;

		finJuego = false;
		inicioJuego = false;
		preguntas = pPreguntas;
		respuestaCorrecta = "";
		acerto = true;
		reto = false;
		verificarInvariante();
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Retorna el socket usado para comunicarse con el jugador 1
	 * @return socketJugador1
	 */
	public Socket darSocketJugador1( )
	{
		return socketJugador1;
	}

	/**
	 * Retorna el administrador de resultados en el que se registran los resultados del encuentro
	 * @return adminResultados
	 */
	public AdministradorJugadores darAdministradorResultados( )
	{
		return adminJugadores;
	}

	/**
	 * Indica si el encuentro ya terminó.
	 * @return Retorna true si el encuentro terminó. Retorna false en caso contrario.
	 */
	public boolean encuentroTerminado( )
	{
		return finJuego;
	}


	/**
	 * Inicia el encuentro y realiza todas las acciones necesarias mientras que este dure.<br>
	 * El ciclo de vida de un encuentro tiene tres partes:<br>
	 * 1. Iniciar el encuentro <br>
	 * 2. Realizar el encuentro (permitir la comunicación entre los clientes)<br>
	 * 3. Terminar el encuentro y enviar la información sobre el ganador
	 */
	public void run( )
	{

		try
		{
			// Iniciar el juego
			turno = generarNumeroAleatorioEnRango(2) + 1;
			iniciarEncuentro();

			while( !finJuego )
			{
				procesarJuego();

			}
		}
		catch( Exception e )
		{
			finJuego = true;

			try
			{
				in1.close( );
				out1.close( );
				socketJugador1.close( );
			}
			catch( IOException e2 )
			{
				e2.printStackTrace( );
			}

			try
			{
				in2.close( );
				out2.close( );
			}
			catch( IOException e2 )
			{
				e2.printStackTrace( );
			}
		}
	}


	/**
	 * Realiza las actividades necesarias para iniciar un encuentro: <br>
	 * 1. Lee la información con los nombres de los jugadores <br>
	 * 2. Consulta los registros de los jugadores <br>
	 * 3. Envía a cada jugador tanto su información como la de su oponente <br>
	 * 4. Le envía a cada jugador un indicador para que sepa si es su turno de jugar. Siempre inicia el juego el primer jugador que se conectó. <br>
	 * @throws Exception 
	 * @throws BatallaNavalException Se lanza esta excepción si hay problemas con el acceso a la base de datos
	 */
	protected void iniciarEncuentro( ) throws Exception
	{

		out1.println(Duelo.INFO + Duelo.SEPARADOR_COMANDO + jugador1.darAliasJugador() + Duelo.SEPARADOR_PARAMETROS + jugador1.darEncuentrosPerdidos() + Duelo.SEPARADOR_PARAMETROS + jugador1.darEncuentrosGanados());
		out1.println(Duelo.INFO + Duelo.SEPARADOR_COMANDO + jugador2.darAliasJugador() + Duelo.SEPARADOR_PARAMETROS + jugador2.darEncuentrosPerdidos() + Duelo.SEPARADOR_PARAMETROS + jugador2.darEncuentrosGanados());

		out2.println(Duelo.INFO + Duelo.SEPARADOR_COMANDO + jugador2.darAliasJugador() + Duelo.SEPARADOR_PARAMETROS + jugador2.darEncuentrosPerdidos() + Duelo.SEPARADOR_PARAMETROS + jugador2.darEncuentrosGanados());
		out2.println(Duelo.INFO + Duelo.SEPARADOR_COMANDO + jugador1.darAliasJugador() + Duelo.SEPARADOR_PARAMETROS + jugador1.darEncuentrosPerdidos() + Duelo.SEPARADOR_PARAMETROS + jugador1.darEncuentrosGanados());

		// Enviar a cada jugador la información sobre en qué orden deben jugar: siempre empieza el jugador 1}
		if( turno == 1 )
		{
			out1.println( turno );
			out2.println( (turno==1)?2:1 );
		}
		else
		{
			out2.println( (turno==2)?1:2 );
			out1.println( turno );
		}

	}

	/**
	 * Este método se encarga de procesar una jugada completa del juego, recibiendo y enviando los mensajes, y además actualizando el puntaje del juego <br>
	 * Si con esta jugada el encuentro debe terminar, entonces el atributo encuentroTerminado del encuentro se convierte en true
	 * @throws BatallaNavalException Se lanza esta excepción si hay problemas con la información que llega
	 * @throws IOException Se lanza esta excepción si hay problemas en la comunicación
	 * @throws BrainsDuelException 
	 */
	private void procesarJuego() throws IOException, BrainsDuelException
	{

		PrintWriter primeroOut = ( turno == 1 ) ? out1 : out2;
		PrintWriter segundoOut = ( turno == 1 ) ? out2 : out1;

		BufferedReader primeroIn = ( turno == 1 ) ? in1 : in2;
		BufferedReader segundoIn = ( turno == 1 ) ? in2 : in1;

		//jugador actual en juego
		RegistroJugador jugadorActual = (turno==1)? jugador1:jugador2;

		if(!inicioJuego)
		{
			primeroOut.println(Duelo.JUEGO);
			inicioJuego = true;
		}
		else
		{
			//Lector primario
			String cadena = primeroIn.readLine();
			String cadena2 = null;

			if(cadena.equals(Duelo.RETO))
			{
				String pregunta = darPreguntaAleatoriaReto();
				respuestaCorrecta = pregunta.split(":::")[5];
				primeroOut.println(pregunta);
				segundoOut.println(pregunta);
				reto = true;

			}

			//separador del comando y datos
			String[] separador = cadena.split(Duelo.SEPARADOR_COMANDO);
			String comando = separador[0];

			if(comando.equals(Duelo.CATEGORIA))
			{

				String categoria = separador[1];
				String pregunta = darPreguntaAleatoria(categoria);
				respuestaCorrecta = pregunta.split(":::")[5];
				primeroOut.println(pregunta);

			}
			else if(comando.equals(Duelo.RESPUESTA))
			{

				String respuesta = separador[1];

				if(reto)
				{
					cadena2 = segundoIn.readLine();
					String respuesta2 = cadena2.split(Duelo.SEPARADOR_COMANDO)[1];

					if(respuesta.equals(respuesta2))
					{

						primeroOut.println(Duelo.GANADOR_RETO + Duelo.SEPARADOR_COMANDO + Duelo.EMPATE);

						segundoOut.println(Duelo.GANADOR_RETO + Duelo.SEPARADOR_COMANDO + Duelo.EMPATE);

					}
					else if(!respuesta.equals(respuestaCorrecta) && !respuesta2.equals(respuestaCorrecta))
					{
						primeroOut.println(Duelo.GANADOR_RETO + Duelo.SEPARADOR_COMANDO + Duelo.EMPATE);

						segundoOut.println(Duelo.GANADOR_RETO + Duelo.SEPARADOR_COMANDO + Duelo.EMPATE);
					}
					else
					{
						darGanadorReto(respuesta2, segundoOut);
						darGanadorReto(respuesta, primeroOut);
					}
					reto = false;
					inicioJuego = false;
					turno = (turno==1)?2:1;

				}
				else{
					if(respuesta.equals(respuestaCorrecta))
					{
						primeroOut.println(Duelo.VALIDACION + Duelo.SEPARADOR_COMANDO + Duelo.CORRECTO);
					}
					else
					{
						primeroOut.println(Duelo.VALIDACION + Duelo.SEPARADOR_COMANDO + Duelo.INCORRECTO);
						acerto = false;
					}
				}

			}
			else if(comando.equals(Duelo.TROFEOS))
			{

				if(separador.length>1)
				{
					String trofeos = separador[1];
					String[] trofeos1 = trofeos.split(Duelo.SEPARADOR_PARAMETROS);

					if(trofeos1.length==3)
					{
						finJuego = true;
						RegistroJugador perdedor = (jugadorActual.equals(jugador1))?jugador2:jugador1;
						terminarEncuentro(jugadorActual, perdedor);
					}
					else if(acerto)
					{
						segundoOut.println(Duelo.TROFEOS + Duelo.SEPARADOR_COMANDO + trofeos);
						inicioJuego = false;
					}
					else
					{
						segundoOut.println(Duelo.TROFEOS + Duelo.SEPARADOR_COMANDO + trofeos);
						acerto = true;
						turno = (turno==1)?2:1;
						inicioJuego = false;

					}
				}
				else
				{
					if(acerto)
					{
						segundoOut.println(Duelo.TROFEOS);
						inicioJuego = false;
					}
					else
					{
						segundoOut.println(Duelo.TROFEOS);
						acerto = true;
						turno = (turno==1)?2:1;
						inicioJuego = false;

					}
				}

			}

		}


	}

	/**
	 * Envia al ganador del reto a ambos jugadores.
	 * @param respuesta Respuesta que introdujo el cliente.
	 * @param Out PrintWriter para comunicarse con el cliente.
	 */
	private void darGanadorReto(String respuesta, PrintWriter Out)
	{
		if(respuesta.equals(respuestaCorrecta))
		{
			Out.println(Duelo.GANADOR_RETO + Duelo.SEPARADOR_COMANDO + Duelo.SI);
		}
		else
		{
			Out.println(Duelo.GANADOR_RETO + Duelo.SEPARADOR_COMANDO + Duelo.NO);
		}
	}
	/**
	 * Realiza las actividades necesarias para terminar un encuentro: <br>
	 * 1. Actualiza los registros de los jugadores en la base de datos <br>
	 * 2. Envía un mensaje a los jugadores advirtiendo sobre el fin del juego y el nombre del ganador <br>
	 * 3. Cierra las conexiones a los jugadores
	 * @throws BatallaNavalException Se lanza esta excepción si hay problemas con el acceso a la base de datos
	 * @throws IOException Se lanza esta excepción si hay problemas en la comunicación
	 */
	private void terminarEncuentro( RegistroJugador ganador, RegistroJugador perdedor) throws IOException, BrainsDuelException
	{
		// Enviar un mensaje indicando el fin del juego y el ganador
		out2.println(Duelo.GANA_JUEGO + Duelo.SEPARADOR_COMANDO + ganador.darAliasJugador());
		out1.println(Duelo.GANA_JUEGO + Duelo.SEPARADOR_COMANDO + ganador.darAliasJugador());

		// Actualizar el registro de los jugadores
		try
		{
			adminJugadores.registrarVictoria( ganador.darNombreJugador( ) );
			adminJugadores.registrarDerrota( perdedor.darNombreJugador( ) );
		}
		catch( SQLException e )
		{
			throw new BrainsDuelException( "Error actualizando la información en la base de datos: " + e.getMessage( ) );
		}
		finally
		{
			cerrarCanales();
		}


		// Cerrar los canales de los jugadores
		cerrarCanales();
	}

	public void cerrarCanales()
	{
		try{
			in1.close( );
			out1.close( );
			out2.close( );
			in2.close( );
			socketJugador1.close( );
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve una pregunta aleatoria.
	 * @param pCategoria categoria de la pregunta actual. pCategoria!=null pCategoria!=""
	 * @return retorna una pregunta aleatoria.
	 */
	private String darPreguntaAleatoria(String pCategoria)
	{
		ArrayList<String> arregloPreguntas = preguntas.darArregloPreguntas(pCategoria);
		int numero = generarNumeroAleatorioEnRango(arregloPreguntas.size());

		return Duelo.PREGUNTA + Duelo.SEPARADOR_COMANDO + arregloPreguntas.get(numero);
	}

	/**
	 * Devuelve una pregunta aleatoria de cualquier categoria
	 * pre: EL estado de juego de ser en reto.
	 * @return retorna una pregunta aleatoria.
	 */
	private String darPreguntaAleatoriaReto()
	{
		ArrayList<String> arregloPreguntas = preguntas.darArregloAleatorio();
		int numero = generarNumeroAleatorioEnRango(arregloPreguntas.size());

		return Duelo.PREGUNTA + Duelo.SEPARADOR_COMANDO + arregloPreguntas.get(numero);
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

	/**
	 * Retorna una cadena con la información del encuentro con el siguiente formato:<br>
	 * <jugador1> ( <puntos> ) - <jugador2> ( <puntos> )
	 * @return cadena
	 */
	public String toString( )
	{
		RegistroJugador j1 = jugador1;
		RegistroJugador j2 = jugador2;

		String cadena = j1.darNombreJugador( ) + " VS " + j2.darNombreJugador( );
		return cadena;
	}


	// -----------------------------------------------------------------
	// Invariante
	// -----------------------------------------------------------------

	/**
	 * Verifica el invariante de la clase
	 * <b>inv:</b><br>
	 * !finJuego => socketJugador1 != null <br>
	 * !finJuego => out1 != null <br>
	 * !finJuego => in1 != null <br>
	 * !finJuego => socketJugador2 != null <br>
	 * !finJuego => out2 != null <br>
	 * !finJuego => in2 != null <br>
	 * jugador1 != null <br>
	 * jugador2 != null <br>
	 */
	private void verificarInvariante( )
	{
		if( !finJuego )
		{
			assert socketJugador1 != null : "Canal inválido";
			assert out1 != null : "Flujo inválido";
			assert in1 != null : "Flujo inválido";
			assert out2 != null : "Flujo inválido";
			assert in2 != null : "Flujo inválido";
		}

		assert jugador1 != null : "Jugador nulo";
		assert jugador2 != null : "Jugador nulo";
		assert inicioJuego == false: "El juego no deberia haber empezado";
		assert preguntas != null : "El banco de preguntas no deberia ser nulo"; 
	}

}
